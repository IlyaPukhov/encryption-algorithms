import React from 'react';
import { Dialog, DialogActions, DialogContent, DialogContentText, DialogTitle, Button } from '@mui/material';

interface ErrorDialogProps {
  open: boolean;
  onClose: () => void;
  errorMessage: string;
}

export const ErrorDialog: React.FC<ErrorDialogProps> = ({ open, onClose, errorMessage }) => {
  return (
    <Dialog
        open={open}
        onClose={onClose}
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
          <Button onClick={onClose} color="primary">Закрыть</Button>
        </DialogActions>
      </Dialog>
  );
};