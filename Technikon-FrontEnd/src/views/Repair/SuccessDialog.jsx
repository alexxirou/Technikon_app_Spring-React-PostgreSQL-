import React from 'react';
import { Dialog, DialogTitle, DialogContent, DialogContentText, DialogActions, Button } from '@mui/material';

const SuccessDialog = ({ success, handleClose, handleGoToRepairs, message }) => {
  return (
    <Dialog open={success} onClose={handleClose}>
      <DialogTitle>{message.title}</DialogTitle>
      <DialogContent>
        <DialogContentText>{message.content}</DialogContentText>
      </DialogContent>
      <DialogActions>
        <Button onClick={handleGoToRepairs} color="primary">
          Go to Repairs
        </Button>
      </DialogActions>
    </Dialog>
  );
};

export default SuccessDialog;
