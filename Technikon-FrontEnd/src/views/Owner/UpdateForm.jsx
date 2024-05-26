// src/components/UpdateOwnerForm.js
import React from 'react';
import { Box, Typography, TextField, Button, CircularProgress } from '@mui/material';

const UpdateOwnerForm = ({
  email,
  address,
  password,
  errors,
  loading,
  handleChange,
  handleSubmit,
  handleClose,
  success,
}) => (
  <Box
    sx={{
      position: 'absolute',
      top: '50%',
      left: '50%',
      transform: 'translate(-50%, -50%)',
      width: 400,
      bgcolor: 'background.paper',
      border: '2px solid #000',
      boxShadow: 24,
      p: 4,
    }}
  >
    <Typography variant="h5">Update Owner</Typography>
    {success && (
      <Typography variant="body1" className="success-dialog">
        Owner details updated successfully. Redirecting...
      </Typography>
    )}
    <form>
      <TextField
        fullWidth
        margin="normal"
        id="email"
        label="Email Address"
        type="email"
        value={email}
        onChange={(e) => handleChange('email', e.target.value)}
        error={!!errors.email}
        helperText={errors.email}
      />
      <TextField
        fullWidth
        margin="normal"
        id="address"
        label="Address"
        type="text"
        value={address}
        onChange={(e) => handleChange('address', e.target.value)}
        error={!!errors.address}
        helperText={errors.address}
      />
      <TextField
        fullWidth
        margin="normal"
        id="password"
        label="Password"
        type="password"
        value={password}
        onChange={(e) => handleChange('password', e.target.value)}
        error={!!errors.password}
        helperText={errors.password}
      />
      {loading ? (
        <CircularProgress />
      ) : (
        <>
          <Button onClick={handleSubmit} variant="contained">
            Update Owner
          </Button>
          <Button onClick={handleClose} variant="contained">
            Cancel
          </Button>
        </>
      )}
    </form>
  </Box>
);

export default UpdateOwnerForm;
