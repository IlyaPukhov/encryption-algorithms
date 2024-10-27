import React from 'react';
import {BrowserRouter as Router, Link, Route, Routes} from 'react-router-dom';
import {CaesarPage} from './pages/CaesarPage';
import {RSAPage} from './pages/RSAPage';
import {AppBar, Button, Container, Toolbar, Typography} from '@mui/material';

export const App: React.FC = () => {
    return (
        <Router>
            <AppBar position="static">
                <Toolbar>
                    <Typography variant="h6" component="div" sx={{flexGrow: 1}}>
                        Encryption SPA
                    </Typography>
                    <Button color="inherit" component={Link} to="/caesar">Caesar Cipher</Button>
                    <Button color="inherit" component={Link} to="/rsa">RSA Cipher</Button>
                </Toolbar>
            </AppBar>
            <Container>
                <Routes>
                    <Route path="/caesar" element={<CaesarPage/>}/>
                    <Route path="/rsa" element={<RSAPage/>}/>
                </Routes>
            </Container>
        </Router>
    );
};