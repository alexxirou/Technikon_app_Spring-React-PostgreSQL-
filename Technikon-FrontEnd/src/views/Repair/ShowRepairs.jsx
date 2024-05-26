import  { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { Box, CircularProgress, Button, Typography, Container } from '@mui/material';
// import { fetchRepairs, updateRepair, deleteRepair } from './apiRepairService';
import { PATHS } from '../../lib/constants';
import { useAuth } from '../../hooks/useAuth';
//import UpdateRepairDialog from './UpdateRepairDialog';
// import ErrorDialog from './ErrorDialog';

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

    const fetchAndSetRepairs = async () => {
      try {
        const repairs = await fetchRepairs(propertyOwnerId);
        setRepairs(repairs);
      } catch (error) {
        console.error("Failed to fetch repairs", error);
      } finally {
        setLoading(false);
      }
    };

    fetchAndSetRepairs();
  }, [propertyOwnerId]);

  const handleCreateRepair = () => {
    navigate(PATHS.CREATE_REPAIR);
  };

  const handleShow = (repairId) => {
    navigate(PATHS.REPAIR_DETAILS(propertyOwnerId, repairId));
  };

  const handleUpdate = (repairId) => {
    const selected = repairs.find((repair) => repair.id === repairId);
    setSelectedRepair(selected);
    setUpdateDialogOpen(true);
  };

  const handleCloseUpdateDialog = () => {
    setUpdateDialogOpen(false);
    setSelectedRepair(null);
  };

  const handleUpdateSubmit = async () => {
    try {
      await updateRepair(propertyOwnerId, selectedRepair.id, selectedRepair);
      navigate(PATHS.REPAIR_DETAILS(propertyOwnerId, selectedRepair.id));
    } catch (error) {
      console.error("Failed to update repair", error);
      setErrorDialogMessage('Failed to update repair');
      setErrorDialogOpen(true);
    }
  };

  const handleDelete = async (repairId) => {
    const repairToDelete = repairs.find((repair) => repair.id === repairId);

    if (repairToDelete && repairToDelete.repairStatus !== 'DEFAULT_PENDING') {
      setErrorDialogMessage("You cannot delete this repair");
      console.log("Repair with status:", repairToDelete.repairStatus, "could not be deleted")
      setErrorDialogOpen(true);
      return;
    }

    try {
      const responseStatus = await deleteRepair(propertyOwnerId, repairId);
      if (responseStatus === 200) {
        setRepairs(repairs.filter((repair) => repair.id !== repairId));
      }
    } catch (error) {
      console.error("Failed to delete repair", error);
      setErrorDialogMessage('Failed to delete repair');
      setErrorDialogOpen(true);
    }
  };

  const handleCloseErrorDialog = () => {
    setErrorDialogOpen(false);
    setErrorDialogMessage('');
  };

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
      <UpdateRepairDialog
        open={updateDialogOpen}
        onClose={handleCloseUpdateDialog}
        repair={selectedRepair}
        onChange={setSelectedRepair}
        onSubmit={handleUpdateSubmit}
      />
      <ErrorDialog
        open={errorDialogOpen}
        message={errorDialogMessage}
        onClose={handleCloseErrorDialog}
      />
    </Container>
  );
};

export default ShowRepairs;
