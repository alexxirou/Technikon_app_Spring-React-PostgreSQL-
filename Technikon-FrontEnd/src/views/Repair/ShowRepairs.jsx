import  { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { Box, CircularProgress, Button, Typography, Container, Snackbar } from '@mui/material';
import { fetchRepairs, fetchAllRepairs, updateRepair, deleteRepair, searchRepairsByDate, searchRepairsByDateRange } from './apiRepairService';
import { PATHS, ROLES} from '../../lib/constants';
import { useAuth } from '../../hooks/useAuth';
import UpdateRepairDialog from './UpdateRepairDialog';
import ErrorDialog from './ErrorDialog';
import SearchByDateDialog from './SearchByDateDialog';
import SearchByDateRangeDialog from './SearchByDateRangeDialog';
import SuccessDialog from './SuccessDialog'; 

const ShowRepairs = () => {
  const [repairs, setRepairs] = useState([]);
  const [loading, setLoading] = useState(true);
  const [errorDialogOpen, setErrorDialogOpen] = useState(false);
  const [errorDialogMessage, setErrorDialogMessage] = useState('');
  const [updateDialogOpen, setUpdateDialogOpen] = useState(false);
  const [selectedRepair, setSelectedRepair] = useState(null);
  const [searchByDateDialogOpen, setSearchByDateDialogOpen] = useState(false);
  const [searchByDateRangeDialogOpen, setSearchByDateRangeDialogOpen] = useState(false);
  const [snackbarOpen, setSnackbarOpen] = useState(false); 
  const [snackbarMessage, setSnackbarMessage] = useState(''); 
  const [successDialogOpen, setSuccessDialogOpen] = useState(false); 
  const { authData } = useAuth();
  const navigate = useNavigate();

  const propertyOwnerId = authData?.userId;

  useEffect(() => {
    if (!propertyOwnerId) {
      return;
    }

    const fetchAndSetRepairs = async () => {
      try {
        let repairs;
        if (authData.authorities.includes("ROLE_ADMIN")) {
          repairs = await fetchAllRepairs();
        } else {
          repairs = await fetchRepairs(propertyOwnerId);
        }
        setRepairs(repairs);
      } catch (error) {
        console.error("Failed to fetch repairs", error);
        setErrorDialogMessage('Failed to fetch repairs');
        setErrorDialogOpen(true);
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

  
  const handleSearchByDate = async (date) => {
    try {
      const repairs = await searchRepairsByDate(propertyOwnerId, date);
      setRepairs(repairs);
      console.log("Repairs with date:", date, repairs);
    } catch (error) {
      console.error("Failed to search repairs by date", error);
      setErrorDialogMessage('Failed to search repairs by date');
      setErrorDialogOpen(true);
    }
  };

  const handleSearchByDateRange = async (startDate, endDate) => {
    try {
      const repairs = await searchRepairsByDateRange(propertyOwnerId, startDate, endDate);
      setRepairs(repairs);
      console.log("Repairs within range of dates:", startDate,"and",endDate, repairs);
    } catch (error) {
      console.error("Failed to search repairs by date range", error);
      setErrorDialogMessage('Failed to search repairs by date range');
      setErrorDialogOpen(true);
    }
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
      const response = await updateRepair(propertyOwnerId, selectedRepair.id, selectedRepair);
      if (response.status === 200) {
        console.log("Repair with id:", selectedRepair.id, "updated successfully");
      }
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
      setErrorDialogOpen(true);
      console.log("Repair with status", repairToDelete.repairStatus, "cannot be deleted")
      return;
    }

    try {
      const responseStatus = await deleteRepair(propertyOwnerId, repairId);
      if (responseStatus === 200) {
        setRepairs(repairs.filter((repair) => repair.id !== repairId));
        console.log("Repair with id:", repairId, "deleted successfully");
        setSnackbarMessage("Repair deleted successfully"); 
        setSnackbarOpen(true); 
        setSuccessDialogOpen(true); 
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

  const handleSnackbarClose = () => {
    setSnackbarOpen(false); 
  };

  const handleSuccessDialogClose = () => {
    setSuccessDialogOpen(false); 
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
        <Box>
        {authData && authData.authorities.includes(ROLES.USER) && (
         <>
          <Button variant="contained" color="primary" onClick={() => setSearchByDateDialogOpen(true)}>
            Search by Date
          </Button>
          <Button variant="contained" color="primary" onClick={() => setSearchByDateRangeDialogOpen(true)} style={{ marginLeft: '8px' }}>
            Search by Date Range
          </Button>
          <Button variant="contained" color="primary" onClick={handleCreateRepair} style={{ marginLeft: '8px' }}>
            Create Repair
          </Button>
          </>
        )}
        </Box>
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
                <Typography variant="subtitle1">{repair.address}</Typography>
                <Typography variant="subtitle1">{repair.dateOfRepair}</Typography>
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
      <SearchByDateDialog
        open={searchByDateDialogOpen}
        onClose={() => setSearchByDateDialogOpen(false)}
        onSearch={handleSearchByDate}
      />
      <SearchByDateRangeDialog
        open={searchByDateRangeDialogOpen}
        onClose={() => setSearchByDateRangeDialogOpen(false)}
        onSearch={handleSearchByDateRange}
      />
      <Snackbar
        open={snackbarOpen}
        autoHideDuration={6000} // Adjust duration as needed
        onClose={handleSnackbarClose}
        message={snackbarMessage}
      />
    </Container>
  );
};

export default ShowRepairs;

