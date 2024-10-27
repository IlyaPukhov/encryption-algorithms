import React from 'react';
import ReactDOM from 'react-dom/client';
import { App } from './App';
import { ThemeProvider, createTheme, CssBaseline } from '@mui/material';

const theme = createTheme({
  palette: {
    mode: 'light',
    primary: {
      main: '#7E57C2',
    },
    secondary: {
      main: '#FFB74D',
    },
    background: {
      default: '#DBD4EC',
      paper: '#FFFFFFE6',
    },
    text: {
      primary: '#333',
      secondary: '#555',
    },
  },
  typography: {
    fontFamily: "'Ubuntu', 'Arial', sans-serif",
  },
  shape: {
    borderRadius: 12,
  },
});

ReactDOM.createRoot(document.getElementById('root') as HTMLElement).render(
  <React.StrictMode>
    <ThemeProvider theme={theme}>
      <CssBaseline />
      <App />
    </ThemeProvider>
  </React.StrictMode>
);
