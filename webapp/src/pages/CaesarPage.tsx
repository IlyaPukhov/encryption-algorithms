import React, {useState} from 'react';
import {Button, TextField, Typography} from '@mui/material';

export const CaesarPage: React.FC = () => {
    const [message, setMessage] = useState<string>('');
    const [key, setKey] = useState<number>(0);
    const [output, setOutput] = useState<string>('');

    const handleEncrypt = async () => {
        // TODO: Добавить логику шифрования
        setOutput(`Encrypted message: ${message} with key: ${key}`);
    };

    const handleDecrypt = async () => {
        // TODO: Добавить логику дешифрования
        setOutput(`Decrypted message: ${message} with key: ${key}`);
    };

    const handleBruteForceDecrypt = async () => {
        // TODO: Добавить логику перебора
        setOutput(`Brute force result for message: ${message}`);
    };

    return (
        <div>
            <Typography variant="h4">Caesar Cipher</Typography>
            <TextField
                label="Message"
                value={message}
                onChange={(e) => setMessage(e.target.value)}
                fullWidth
                multiline
            />
            <TextField
                label="Key"
                type="number"
                value={key}
                onChange={(e) => setKey(parseInt(e.target.value))}
                fullWidth
            />
            <Button variant="contained" onClick={handleEncrypt}>Encrypt</Button>
            <Button variant="contained" onClick={handleDecrypt}>Decrypt</Button>
            <Typography variant="h6">Brute Force Decryption</Typography>
            <Button variant="contained" onClick={handleBruteForceDecrypt}>Start Brute Force</Button>
            <Typography variant="body1">Result: {output}</Typography>
        </div>
    );
};