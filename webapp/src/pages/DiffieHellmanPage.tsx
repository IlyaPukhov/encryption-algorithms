import React, { useState } from 'react';
import { Box, Button, Card, CardContent, CircularProgress, Stack, Typography } from '@mui/material';
import { API_BASE_URL } from '../config/config';
import { BigTextDisplay } from '../components/BigTextDisplay';

export const DiffieHellmanPage: React.FC = () => {
  const [output, setOutput] = useState<any>(null);
  const [loading, setLoading] = useState<boolean>(false);
  const [error, setError] = useState<string>('');

  function parseBigIntegers(textData: string) {
    const modifiedTextData = textData.replace(/(\d{16,})/g, '"$1"');

    return JSON.parse(modifiedTextData, (_key, value) => {
      if (typeof value === 'string' && /^\d+$/.test(value)) {
        return BigInt(value);
      }
      return value;
    });
  }


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

      const data = await response.text();
      setOutput(parseBigIntegers(data));
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
        minHeight: '500px',
      }}
    >
      <CardContent>
        <Typography variant="h4" gutterBottom sx={{ color: 'primary.main' }}>
          Протокол Диффи-Хеллмана
        </Typography>
        <Stack spacing={3} mt={2}>
          <Stack direction="row" spacing={2} justifyContent="center">
            <Button variant="contained" color="primary" onClick={handleGenerateSharedSecret} disabled={loading}>
              Вычислить общий секретный ключ
            </Button>
          </Stack>
          <Typography variant="h6">Результат</Typography>
          {loading ? (
            <Box sx={{
              display: 'flex',
              justifyContent: 'center',
              alignItems: 'center',
              height: '250px',
            }}>
              <CircularProgress />
            </Box>
          ) : error ? (
            <Typography variant="body1" sx={{ color: 'error.main' }}>
              {error}
            </Typography>
          ) : (
            output && (
              <Stack spacing={3} mt={2}>
                <Box sx={{ textAlign: 'center' }}>
                  <Typography variant="body1">Параметр w: &nbsp;<BigTextDisplay variant="body1" value={output.w} /></Typography>
                  <Typography variant="body1">Параметр n: &nbsp;<BigTextDisplay variant="body1" value={output.n} /></Typography>
                </Box>

                <Stack direction="row" spacing={2} justifyContent="center" sx={{ padding: "16px" }}>
                  <Stack spacing={1} sx={{ paddingLeft: "20px" }}>
                    <Typography variant="h6">Сторона A</Typography>
                    <Typography variant="body1">Секретный ключ (xA): <BigTextDisplay variant="body1" value={output.xa} /></Typography>
                    <Typography variant="body1">Открытый ключ (yA): <BigTextDisplay variant="body1" value={output.ya} /></Typography>
                  </Stack>
                  <Stack spacing={1} sx={{ textAlign: 'right', paddingRight: "20px" }}>
                    <Typography variant="h6">Сторона B</Typography>
                    <Typography variant="body1">Секретный ключ (xB): <BigTextDisplay variant="body1" value={output.xb} /></Typography>
                    <Typography variant="body1">Открытый ключ (yB): <BigTextDisplay variant="body1" value={output.yb} /></Typography>
                  </Stack>
                </Stack>


                <Box sx={{ textAlign: 'center', mt: 3 }}>
                  <Typography variant="h5" sx={{ fontWeight: 'bold' }}>
                    Общий секретный ключ (kAB): <BigTextDisplay variant="h5" value={output.kab} />
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