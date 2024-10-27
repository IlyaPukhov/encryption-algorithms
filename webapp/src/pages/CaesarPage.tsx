import React, { useState } from 'react';
import { TextField, Button, Typography, Card, CardContent, Stack, Dialog, DialogActions, DialogContent, DialogContentText, DialogTitle } from '@mui/material';
import { useErrorHandler } from '../hooks/useErrorHandler';
import { useKeyChange } from '../hooks/useKeyChange';
import { isEmptyOrNull } from '../utils/stringUtils';

export const CaesarPage: React.FC = () => {
  const [message, setMessage] = useState<string>('');
  const [output, setOutput] = useState<string>('');
  const { key, handleKeyChange } = useKeyChange(1);
  const { openErrorDialog, errorMessage, handleCloseErrorDialog, showError } = useErrorHandler();

  const handleEncrypt = async () => {
    const response = await fetch('http://localhost:8080/api/caesar_cipher/encrypt', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({
        message: message,
        key: key,
      }),
    });

    const data = await response.json();
    if (response.ok) {
      setOutput(`Зашифрованное сообщение: "${data.message}"`);
    } else {
      showError(data);
    }
  };

  const handleDecrypt = async () => {
    const response = await fetch('http://localhost:8080/api/caesar_cipher/decrypt', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({
        message: message,
        key: key,
      }),
    });

    const data = await response.json();
    if (response.ok) {
      setOutput(`Расшифрованное сообщение: "${data.message}"`);
    } else {
      showError(data);
    }
  };

  const handleBruteforceDecrypt = async () => {
    const url = new URL('http://localhost:8080/api/caesar_cipher/hack/bruteforce');

    url.searchParams.append('isDefault', String(isEmptyOrNull(message)));
    const requestBody: any = {};
    if (!isEmptyOrNull(message)) {
      requestBody.message = message;
      requestBody.key = String(key)
    }

    const response = await fetch(url, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: Object.keys(requestBody).length ? JSON.stringify(requestBody) : undefined,
    });

    const data = await response.json();
    if (response.ok) {
      setOutput(`
        Время перебора: ${data.bruteforce_time} мс <br />
        Ключ: ${data.key} <br />
        Исходное сообщение: "${data.raw_message}"
      `);
    } else {
      console.log(data)
      showError(data);
    }
  };

  return (
    <Card
      sx={{
        maxWidth: 600,
        mx: 'auto',
        mt: 4,
        p: 2,
        backgroundColor: 'background.paper',
        boxShadow: '0px 4px 12px rgba(0, 0, 0, 0.1)',
        borderRadius: 2,
      }}
    >
      <CardContent>
        <Typography variant="h4" gutterBottom sx={{ color: 'primary.main' }}>
          Шифр Цезаря
        </Typography>
        <Stack spacing={3} mt={2}>
          <TextField
            label="Сообщение"
            value={message}
            onChange={(e) => setMessage(e.target.value)}
            fullWidth
            multiline
            rows={4}
            variant="outlined"
            sx={{
              wordBreak: 'break-word',
              overflowWrap: 'break-word',
              whiteSpace: 'normal',
              color: 'text.primary',
            }}
          />
          <TextField
            label="Ключ"
            type="number"
            value={key}
            onChange={handleKeyChange}
            fullWidth
            variant="outlined"
            sx={{
              '& input[type=number]::-webkit-inner-spin-button, & input[type=number]::-webkit-outer-spin-button': {
                display: 'none',
              },
              '& input[type=number]': {
                MozAppearance: 'textfield',
              },
            }}
          />
          <Stack direction="row" spacing={2} justifyContent="center">
            <Button variant="contained" color="primary" onClick={handleEncrypt}>
              Зашифровать
            </Button>
            <Button variant="contained" color="secondary" onClick={handleDecrypt}>
              Расшифровать
            </Button>
          </Stack>
          <Button variant="outlined" color="warning" onClick={handleBruteforceDecrypt} fullWidth>
            Дешифровать перебором
          </Button>
          <Typography variant="h6">Результат</Typography>
          <Typography
            variant="body1"
            sx={{
              wordBreak: 'break-word',
              overflowWrap: 'break-word',
              whiteSpace: 'normal',
              color: 'text.primary',
            }}
            dangerouslySetInnerHTML={{ __html: output }}
          />
        </Stack>
      </CardContent>

      {/* Модальное окно для обработки ошибок */}
      <Dialog
        open={openErrorDialog}
        onClose={handleCloseErrorDialog}
      >
        <DialogTitle sx={{ color: 'crimson' }}>Ошибка!</DialogTitle>
        <DialogContent>
          <DialogContentText
            variant="body1"
            sx={{
              wordBreak: 'break-word',
              overflowWrap: 'break-word',
              whiteSpace: 'normal',
              color: 'text.primary',
            }}
            dangerouslySetInnerHTML={{ __html: errorMessage }}
          />
        </DialogContent>
        <DialogActions>
          <Button onClick={handleCloseErrorDialog} color="primary">Закрыть</Button>
        </DialogActions>
      </Dialog>
    </Card>
  );
};