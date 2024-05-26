import { Dialog, DialogActions, DialogContent, DialogContentText, DialogTitle, Button } from '@mui/material';

const SignupDialog = ({ success, navigate, PATHS }) => {
  return (
    <Dialog open={success} onClose={() => setSuccess(false)}>
      <DialogTitle>Signup Successful</DialogTitle>
      <DialogContent>
        <DialogContentText>
          Your account has been successfully created. You will be redirected to the login page shortly.
        </DialogContentText>
      </DialogContent>
      <DialogActions>
        <Button onClick={() => navigate(PATHS.LOGIN)} color="primary">
          Go to Login
        </Button>
      </DialogActions>
    </Dialog>
  );
};

export default SignupDialog;
