import React, { useState } from 'react';
import { TextField, Button, Typography, Card, CardContent, Stack } from '@mui/material';

export const RSAPage: React.FC = () => {
  const [message, setMessage] = useState<string>('');
  const [publicKey, setPublicKey] = useState<string>('');
  const [privateKey, setPrivateKey] = useState<string>('');
  const [output, setOutput] = useState<string>('');

  const handleEncrypt = async () => {
    setOutput(`Encrypted with RSA: ${message}`);
  };

  const handleDecrypt = async () => {
    setOutput(`Decrypted with RSA: ${message}`);
  };

  const handleGenerateKeys = async () => {
    setPublicKey("Sample Public Key");
    setPrivateKey("Sample Private Key");
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
          <Typography variant="body1" sx={{ wordBreak: 'break-all', color: 'text.primary' }}>
            {output}
          </Typography>
        </Stack>
      </CardContent>
    </Card>
  );
};