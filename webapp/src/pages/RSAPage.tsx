import React, { useState } from 'react';
import { TextField, Button, Typography, Card, CardContent, Stack } from '@mui/material';
import { ErrorDialog } from '../components/ErrorDialog';
import { useErrorHandler } from '../hooks/useErrorHandler';
import { API_BASE_URL } from '../config/config';

export const RSAPage: React.FC = () => {
  const [message, setMessage] = useState<string>('');
  const [publicKey, setPublicKey] = useState<string>('');
  const [privateKey, setPrivateKey] = useState<string>('');
  const [output, setOutput] = useState<string>('');
  const { openErrorDialog, errorMessage, handleCloseErrorDialog, showError } = useErrorHandler();

  const handleEncrypt = async () => {
    const response = await fetch(`${API_BASE_URL}/api/rsa_cipher/encrypt`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({
        message: message,
        key: publicKey,
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
    const response = await fetch(`${API_BASE_URL}/api/rsa_cipher/decrypt`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({
        message: message,
        key: privateKey,
      }),
    });

    const data = await response.json();
    if (response.ok) {
      setOutput(`Расшифрованное сообщение: "${data.message}"`);
    } else {
      showError(data);
    }
  };

  const handleGenerateKeys = async () => {
    const publicKey = await fetchKey('api/rsa_cipher/public_key');
    setPublicKey(publicKey);

    const privateKey = await fetchKey('api/rsa_cipher/private_key');
    setPrivateKey(privateKey);
  };

  const fetchKey = async (endpoint: string) => {
    const response = await fetch(`${API_BASE_URL}/${endpoint}`);
    const data = await response.json();
    return data.key;
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
          Алгоритм RSA
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
            label="Открытый ключ"
            value={publicKey}
            onChange={(e) => setPublicKey(e.target.value)}
            fullWidth
            variant="outlined"
          />
          <TextField
            label="Закрытый ключ"
            value={privateKey}
            onChange={(e) => setPrivateKey(e.target.value)}
            fullWidth
            variant="outlined"
          />
          <Stack direction="row" spacing={2} justifyContent="center">
            <Button variant="contained" color="primary" onClick={handleEncrypt}>
              Зашифровать
            </Button>
            <Button variant="contained" color="secondary" onClick={handleDecrypt}>
              Расшифровать
            </Button>
          </Stack>
          <Button variant="outlined" color="success" onClick={handleGenerateKeys} fullWidth>
            Получить ключи
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

      <ErrorDialog
        open={openErrorDialog}
        onClose={handleCloseErrorDialog}
        errorMessage={errorMessage}
      />
    </Card>
  );
};