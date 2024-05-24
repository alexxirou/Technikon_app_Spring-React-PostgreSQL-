import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { Box, CircularProgress, Button, Typography, Container, Dialog, DialogTitle, DialogContent, DialogContentText, DialogActions } from '@mui/material';
import api from '../../api/Api';
import { PATHS } from '../../lib/constants';
import { useAuth } from '../../hooks/useAuth';
import { TextField } from '@mui/material';

const ShowRepairs = () => {
  const [repairs, setRepairs] = useState([]);
  const [loading, setLoading] = useState(true);
  const [errorDialogOpen, setErrorDialogOpen] = useState(false);
  const [errorDialogMessage, setErrorDialogMessage] = useState('');
  const [updateDialogOpen, setUpdateDialogOpen] = useState(false);
  const [selectedRepair, setSelectedRepair] = useState(null);
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
    navigate(PATHS.REPAIR_DETAILS(propertyOwnerId,repairId));
  };


  const handleUpdate = (repairId) => {
    // Find the selected repair based on the repairId
    const selected = repairs.find(repair => repair.id === repairId);
    setSelectedRepair(selected);
    setUpdateDialogOpen(true);
  };

  const handleCloseUpdateDialog = () => {
    setUpdateDialogOpen(false);
    setSelectedRepair(null);
  };

  const handleUpdateSubmit = async () => {
    try {
      const reponse = await api.put(`/api/property-repairs/${propertyOwnerId}/${selectedRepair.id}`, selectedRepair);
      setUpdateDialogOpen(false);
      console.log(reponse.data);
      // Update repairs state or refetch repairs to reflect the changes
    } catch (error) {
      console.error("Failed to update repair", error);
      setErrorDialogMessage('Failed to update repair');
      setErrorDialogOpen(true);
    }
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
      const response = await api.delete(`/api/property-repairs/${propertyOwnerId}/delete/${repairId}`);
    
        setRepairs(repairs.filter(repair => repair.id !== repairId));
        console.log("Response status:", response.status); // Log the response status
      
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
      {/* Update Dialog */}
    <Dialog open={updateDialogOpen} onClose={handleCloseUpdateDialog}>
      <DialogTitle>Update Repair</DialogTitle>
      <DialogContent>
        <DialogContentText>Update the repair details below:</DialogContentText>
        {/* Form fields for updating repair */}
        <TextField
          label="Short Description"
          value={selectedRepair?.shortDescription || ''}
          onChange={(e) => setSelectedRepair({ ...selectedRepair, shortDescription: e.target.value })}
          fullWidth
          margin="normal"
        />
        <TextField
          label="Date of Repair"
          type="date"
          value={selectedRepair?.dateOfRepair || ''}
          onChange={(e) => setSelectedRepair({ ...selectedRepair, dateOfRepair: e.target.value })}
          fullWidth
          margin="normal"
          InputLabelProps={{
          shrink: true,
          }}
        />
        <TextField
          label="Repair Type"
          value={selectedRepair?.repairType || ''}
          onChange={(e) => setSelectedRepair({ ...selectedRepair, repairType: e.target.value })}
          fullWidth
          margin="normal"
        />
        <TextField
          label="Repair Status"
          value={selectedRepair?.repairStatus || ''}
          onChange={(e) => setSelectedRepair({ ...selectedRepair, repairStatus: e.target.value })}
          fullWidth
          margin="normal"
        />
        <TextField
          label="Cost"
          value={selectedRepair?.cost || ''}
          onChange={(e) => setSelectedRepair({ ...selectedRepair, cost: e.target.value })}
          fullWidth
          margin="normal"
        />
        <TextField
          label="Long Description"
          value={selectedRepair?.longDescription || ''}
          onChange={(e) => setSelectedRepair({ ...selectedRepair, longDescription: e.target.value })}
          fullWidth
          margin="normal"
        />
      </DialogContent>
      <DialogActions>
        <Button onClick={handleCloseUpdateDialog} color="primary">Cancel</Button>
        <Button onClick={handleUpdateSubmit} color="primary">Update</Button>
      </DialogActions>
    </Dialog>
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
  )}

export default ShowRepairs;
