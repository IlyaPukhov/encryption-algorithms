import React, { useState } from 'react';
import { Box, Button, Card, CardContent, CircularProgress, Stack, Typography } from '@mui/material';
import { API_BASE_URL } from '../config/config';

export const DiffieHellmanPage: React.FC = () => {
  const [output, setOutput] = useState<any>(null);
  const [loading, setLoading] = useState<boolean>(false);
  const [error, setError] = useState<string>('');

  const handleGenerateSharedSecret = async () => {
    setLoading(true);
    setError('');

    try {
      const response = await fetch(`${API_BASE_URL}/api/diffie_hellman`, {
        method: 'GET',
        headers: { 'Content-Type': 'application/json' },
      });

      if (!response.ok) {
        throw new Error('Ошибка при получении данных');
      }

      const data = await response.json();
      setOutput(data);
    } catch (err) {
      setError('Ошибка при вычислении общего секретного ключа.');
    } finally {
      setLoading(false);
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
          Протокол Диффи-Хеллмана (выработка общего секретного ключа)
        </Typography>
        <Stack spacing={3} mt={2}>
          <Stack direction="row" spacing={2} justifyContent="center">
            <Button variant="contained" color="primary" onClick={handleGenerateSharedSecret} disabled={loading}>
              Вычислить
            </Button>
          </Stack>
          <Typography variant="h6">Результат</Typography>
          {loading ? (
            <Box sx={{ display: 'flex', justifyContent: 'center', mt: 2 }}>
              <CircularProgress/>
            </Box>
          ) : error ? (
            <Typography variant="body1" sx={{ color: 'error.main' }}>
              {error}
            </Typography>
          ) : (
            output && (
              <Stack spacing={3} mt={2}>
                <Box sx={{ textAlign: 'center' }}>
                  <Typography variant="body1">Параметр w: <strong>{output.w}</strong></Typography>
                  <Typography variant="body1">Параметр n: <strong>{output.n}</strong></Typography>
                </Box>

                <Stack spacing={1}>
                  <Typography variant="h6">Сторона A</Typography>
                  <Typography variant="body1">Секретный ключ A (xA): <strong>{output.xA}</strong></Typography>
                  <Typography variant="body1">Открытый ключ A (yA): <strong>{output.yA}</strong></Typography>
                </Stack>

                <Stack spacing={1}>
                  <Typography variant="h6">Сторона B</Typography>
                  <Typography variant="body1">Секретный ключ B (xB): <strong>{output.xB}</strong></Typography>
                  <Typography variant="body1">Открытый ключ B (yB): <strong>{output.yB}</strong></Typography>
                </Stack>

                <Box sx={{ textAlign: 'center', mt: 3 }}>
                  <Typography variant="h5" sx={{ fontWeight: 'bold' }}>
                    Общий секретный ключ (kAB): <strong>{output.kAB}</strong>
                  </Typography>
                </Box>
              </Stack>
            )
          )}
        </Stack>
      </CardContent>
    </Card>
  );
};