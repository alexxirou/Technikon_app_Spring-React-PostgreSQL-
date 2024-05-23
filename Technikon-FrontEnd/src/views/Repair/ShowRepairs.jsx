import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { Box, CircularProgress, Button, Typography, Container, Dialog, DialogTitle, DialogContent, DialogContentText, DialogActions } from '@mui/material';
import api from '../../api/Api';
import { PATHS } from '../../lib/constants';
import { useAuth } from '../../hooks/useAuth';

const ShowRepairs = () => {
  const [repairs, setRepairs] = useState([]);
  const [loading, setLoading] = useState(true);
  const [errorDialogOpen, setErrorDialogOpen] = useState(false);
  const [errorDialogMessage, setErrorDialogMessage] = useState('');
  const { authData } = useAuth();
  const navigate = useNavigate();

  const propertyOwnerId = authData?.userId;

  useEffect(() => {
    if (!propertyOwnerId) {
      return;
    }

    const fetchRepairs = async () => {
      try {
        const response = await api.get(`/api/property-repairs/all-by-owner/${propertyOwnerId}`);
        setRepairs(response.data);
      } catch (error) {
        console.error("Failed to fetch repairs", error);
      } finally {
        setLoading(false);
      }
    };

    fetchRepairs();
  }, [propertyOwnerId]);

  if (loading) {
    return (
      <Box display="flex" justifyContent="center" alignItems="center" height="100vh">
        <CircularProgress />
      </Box>
    );
  }

  if (!propertyOwnerId) {
    return (
      <Typography variant="h6" align="center">
        User not authenticated.
      </Typography>
    );
  }

  const handleCreateRepair = () => {
    navigate(PATHS.CREATE_REPAIR);
  };


  const handleShow = (repairId) => {
    navigate(PATHS.REPAIR_DETAILS.replace(':propertyOwnerId', propertyOwnerId).replace(':repairId', repairId));
  };

  const handleUpdate = (repairId) => {
    navigate(`/update/${repairId}`);
  };

  const handleDelete = async (repairId) => {
    // Get the repair object from the state based on the repairId
    const repairToDelete = repairs.find(repair => repair.id === repairId);
    
    // Check if the repair has a status other than "DEFAULT_PENDING"
    if (repairToDelete && repairToDelete.repairStatus !== "DEFAULT_PENDING") {
      setErrorDialogMessage("You cannot delete this repair.");
      setErrorDialogOpen(true);
      return; // Exit the function without making the delete request
    }
  
    // If the repair has status "DEFAULT_PENDING", proceed with the delete request
    try {
      await api.delete(`/api/property-repairs/${propertyOwnerId}/delete/${repairId}`);
      setRepairs(repairs.filter(repair => repair.id !== repairId));
    } catch (error) {
      console.error("Failed to delete repair", error);
    }
  };

  const handleCloseErrorDialog = () => {
    setErrorDialogOpen(false);
    setErrorDialogMessage('');
  };

  return (
    <Container maxWidth="md">
      <Box mt={2} mb={2} display="flex" justifyContent="space-between" alignItems="center">
        <Typography variant="h4">Repairs</Typography>
        <Button variant="contained" color="primary" onClick={handleCreateRepair}>
          Create Repair
        </Button>
      </Box>
      <Box>
        {repairs.length === 0 ? (
          <Typography variant="h6" align="center">
            No repairs found.
          </Typography>
        ) : (
          repairs.map((repair) => (
            <Box
              key={repair.id}
              display="flex"
              alignItems="center"
              justifyContent="space-between"
              p={2}
              mb={2}
              border={1}
              borderRadius={8}
              borderColor="grey.300"
            >
              <Box>
                <Typography variant="subtitle1">{repair.shortDescription}</Typography>
                <Typography variant="body2" color="textSecondary">
                  {repair.repairStatus}
                </Typography>
              </Box>
              <Box>
                <Button variant="contained" color="primary" onClick={() => handleShow(repair.id)}>
                  Show
                </Button>
                <Button variant="contained" color="secondary" onClick={() => handleUpdate(repair.id)} style={{ marginLeft: '8px' }}>
                  Update
                </Button>
                <Button variant="contained" color="error" onClick={() => handleDelete(repair.id)} style={{ marginLeft: '8px' }}>
                  Delete
                </Button>
              </Box>
            </Box>
          ))
        )}
      </Box>
      <Dialog open={errorDialogOpen} onClose={handleCloseErrorDialog}>
        <DialogTitle>Error</DialogTitle>
        <DialogContent>
          <DialogContentText>{errorDialogMessage}</DialogContentText>
        </DialogContent>
        <DialogActions>
          <Button onClick={handleCloseErrorDialog} color="primary">
            OK
          </Button>
        </DialogActions>
      </Dialog>
    </Container>
  );
};

export default ShowRepairs;
