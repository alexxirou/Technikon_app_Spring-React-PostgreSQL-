import React, { useState } from 'react';
import { Button, Snackbar } from '@mui/material';
import { deleteProperties } from './ApiService'; // Assuming you have an ApiService with a deleteProperties function

const DeletePropertyButton = ({ propertyId, onSuccess, onError }) => {
  const [snackbarOpen, setSnackbarOpen] = useState(false);
  const [snackbarMessage, setSnackbarMessage] = useState('');

  const handleDeleteProperty = async () => {
    try {
      const status = await deleteProperties(propertyId);
      if (status === 200) {
        onSuccess(); // Function to execute on successful deletion
        setSnackbarMessage('Property deleted successfully');
        setSnackbarOpen(true);
      } else {
        onError(); // Function to execute on deletion error
        setSnackbarMessage('Failed to delete property');
        setSnackbarOpen(true);
      }
    } catch (error) {
      console.error('Failed to delete property', error);
      onError(); // Function to execute on deletion error
      setSnackbarMessage('Failed to delete property');
      setSnackbarOpen(true);
    }
  };

  const handleSnackbarClose = () => {
    setSnackbarOpen(false);
  };

  return (
    <>
      <Button variant="contained" color="error" onClick={handleDeleteProperty}>
        Delete
      </Button>
      <Snackbar
        open={snackbarOpen}
        autoHideDuration={6000}
        onClose={handleSnackbarClose}
        message={snackbarMessage}
      />
    </>
  );
};

export default DeletePropertyButton;
