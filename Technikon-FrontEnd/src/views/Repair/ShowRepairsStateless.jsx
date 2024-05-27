import React from 'react';
import { Box, CircularProgress, Button, Typography, Container, Snackbar } from '@mui/material';
import { useAuth } from '../../hooks/useAuth';
import UpdateRepairDialog from './UpdateRepairDialog';
import ErrorDialog from './ErrorDialog';
import SearchByDateDialog from './SearchByDateDialog';
import SearchByDateRangeDialog from './SearchByDateRangeDialog';
import SuccessDialog from './SuccessDialog';

const ShowRepairsStateless = ({
  repairs,
  loading,
  errorDialogOpen,
  errorDialogMessage,
  updateDialogOpen,
  selectedRepair,
  searchByDateDialogOpen,
  searchByDateRangeDialogOpen,
  snackbarOpen,
  snackbarMessage,
  successDialogOpen,
  handleCreateRepair,
  handleShow,
  handleSearchByDate,
  handleSearchByDateRange,
  handleUpdate,
  handleCloseUpdateDialog,
  handleUpdateSubmit,
  handleDelete,
  handleCloseErrorDialog,
  handleSnackbarClose,
  handleSuccessDialogClose,
  navigate,
  propertyOwnerId,
  authData
}) => {


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
          {authData && authData.authorities.includes("ROLE_USER") && (
            <>
              <Button variant="contained" color="primary" onClick={() => searchByDateDialogOpen(true)}>
                Search by Date
              </Button>
              <Button variant="contained" color="primary" onClick={() => searchByDateRangeDialogOpen(true)} style={{ marginLeft: '8px' }}>
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
      <Snackbar
        open={snackbarOpen}
        autoHideDuration={6000} // Adjust duration as needed
        onClose={handleSnackbarClose}
        message={snackbarMessage}
      />
      <SuccessDialog
        open={successDialogOpen}
        onClose={handleSuccessDialogClose}
      />
    </Container>
  );
};

export default ShowRepairsStateless;
