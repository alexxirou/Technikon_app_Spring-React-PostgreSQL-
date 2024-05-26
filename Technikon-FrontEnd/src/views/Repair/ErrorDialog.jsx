import React from 'react';
import { Dialog, DialogTitle, DialogContent, DialogContentText, DialogActions, Button } from '@mui/material';

const ErrorDialog = ({ open, message, onClose }) => {
  return (
    <Dialog open={open} onClose={onClose}>
      <DialogTitle>Error</DialogTitle>
      <DialogContent>
        <DialogContentText>{message}</DialogContentText>
      </DialogContent>
      <DialogActions>
        <Button onClick={onClose} color="primary">OK</Button>
      </DialogActions>
    </Dialog>
  );
};

export default ErrorDialog;
