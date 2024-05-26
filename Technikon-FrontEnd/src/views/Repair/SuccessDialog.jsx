
import { Dialog, DialogTitle, DialogContent, DialogContentText, DialogActions, Button } from '@mui/material';

const SuccessDialog = ({ success, handleClose, handleGoToRepairs }) => {
  return (
    <Dialog open={success} onClose={handleClose}>
      <DialogTitle>Repair created!</DialogTitle>
      <DialogContent>
        <DialogContentText>
          The property repair has been created successfully. You can now go to the repairs page.
        </DialogContentText>
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

