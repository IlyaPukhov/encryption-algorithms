import { useState } from 'react';

export const useErrorHandler = () => {
    const [openErrorDialog, setOpenErrorDialog] = useState<boolean>(false);
    const [errorMessage, setErrorMessage] = useState<string>('');

    const handleCloseErrorDialog = () => setOpenErrorDialog(false);

    const showError = (data: any) => {
        setErrorMessage(`
            Детали: ${data.detail} <br />
            Ошибки: [ <br />
                ${data.errors.map((error: string) => `&nbsp;&nbsp;&nbsp;&nbsp;"${error}"`).join(', <br />')} <br />
            ]`);
        setOpenErrorDialog(true);
    };

    return { openErrorDialog, errorMessage, handleCloseErrorDialog, showError };
};