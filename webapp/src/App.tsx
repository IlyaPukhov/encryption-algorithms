import React, { useState } from 'react';
import { AppBar, Tabs, Tab, Box, Container } from '@mui/material';
import { CaesarPage } from './pages/CaesarPage';
import { RSAPage } from './pages/RSAPage';

export const App: React.FC = () => {
  const [value, setValue] = useState<number>(0);

  const handleChange = (_event: React.SyntheticEvent, newValue: number) => {
    setValue(newValue);
  };

  return (
    <Container
      maxWidth="md"
      sx={{
        mt: 4,
        minHeight: '100vh',
        borderRadius: 2,
        padding: 2
      }}
    >
      <AppBar position="relative" sx={{
        backgroundColor: '#FBFCFC',
        boxShadow: '0px 4px 8px rgba(0, 0, 0, 0.1)',
        borderRadius: '8px',
        width: '80%',
        paddingTop: 0.3,
        mx: 'auto'
      }}>
        <Tabs
          value={value}
          onChange={handleChange}
          centered
          sx={{
            '& .MuiTabs-indicator': {
              backgroundColor: '#7E57C2',
            },
            '& .MuiTab-root': {
              textTransform: 'none',
              fontWeight: 'bold',
              color: '#333',
              fontSize: '1.2rem',
              padding: '16px 32px',
              borderRadius: '16px',
              '&:hover': {
                backgroundColor: 'rgba(126, 87, 194, 0.1)',
              },
              '&.Mui-selected': {
                color: '#7E57C2',
                backgroundColor: 'rgba(126, 87, 194, 0.2)',
              },
            },
          }}
        >
          <Tab label="Шифр Цезаря" />
          <Tab label="Алгоритм RSA" />
        </Tabs>
      </AppBar>
      <Box sx={{ p: 3 }}>
        {value === 0 && <CaesarPage />}
        {value === 1 && <RSAPage />}
      </Box>
    </Container>
  );
};