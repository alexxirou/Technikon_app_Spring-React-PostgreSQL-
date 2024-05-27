import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { Box, CircularProgress, Button, Typography, Container, Snackbar } from '@mui/material';
import { fetchProperty, searchPropertiesByTin, searchPropertiesByArea, updateProperties, deleteProperties } from './ApiPropertyService';
import { PATHS } from '../../lib/constants';
import { useAuth } from '../../hooks/useAuth';
// import UpdateRepairDialog from '../../UpdateRepairDialog';
// import ErrorDialog from '../../ErrorDialog';
// import SearchByDateDialog from '../../SearchByDateDialog';
// import SearchByDateRangeDialog from '../../SearchByDateRangeDialog';

const ShowProperties = () => {
    const [properties, setProperties] = useState([]);
    const [loading, setLoading] = useState(true);
    const [errorDialogOpen, setErrorDialogOpen] = useState(false);
    const [errorDialogMessage, setErrorDialogMessage] = useState('');
    const [updateDialogOpen, setUpdateDialogOpen] = useState(false);
    const [selectedProperty, setSelectProperty] = useState(null);
    const [searchByTinDialogOpen, setSearchByTin] = useState(false);
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
  
      const fetchAndSetProperties = async () => {
        try {
          const properties = await fetchProperty(propertyOwnerId);
          setProperties(properties);
        } catch (error) {
          console.error("Failed to fetch properties", error);
        } finally {
          setLoading(false);
        }
      };
  
      fetchAndSetProperties();
    }, [propertyOwnerId]);
  
    const handleCreateRepair = () => {
      navigate(PATHS.CREATE_PROPERTY);
    };
  
    const handleShow = (propertyId) => {
      navigate(PATHS.PROPERTY_DETAILS(propertyOwnerId, propertyId));
    };
  
    const handleSearchByDate = async (tin) => {
      try {
        const properties = await searchPropertiesByTin(propertyOwnerId, tin);
        setProperties(properties);
        console.log("Properties with tin:", tin, properties);
      } catch (error) {
        console.error("Failed to search properties by tin", error);
        setErrorDialogMessage('Failed to search properties by tin');
        setErrorDialogOpen(true);
      }
    };
  
    const handleSearchByArea = async (latitude, longitude) => {
      try {
        const properties = await searchPropertiesByArea(propertyOwnerId, latitude, longitude);
        setProperties(properties);
        console.log("The Area is latitude ", latitude,"and longitude",longitude, properties);
      } catch (error) {
        console.error("Failed to search property area", error);
        setErrorDialogMessage('Failed to search property area');
        setErrorDialogOpen(true);
      }
    };
  
    const handleUpdate = (propertyId) => {
      const selected = properties.find((property) => property.id === propertyId);
      setSelectProperty(selected);
      setUpdateDialogOpen(true);
    };
  
    const handleCloseUpdateDialog = () => {
      setUpdateDialogOpen(false);
      setSelectProperty(null);
    };
  
    const handleUpdateSubmit = async () => {
      try {
        const response = await updateProperties(propertyOwnerId, selectedProperty.id, selectedProperty);
        if (response.status === 200) {
          console.log("Repair with id:", selectedProperty.id, "updated successfully");
        }
        navigate(PATHS.REPAIR_DETAILS(propertyOwnerId, selectedProperty.id));
      } catch (error) {
        console.error("Failed to update property", error);
        setErrorDialogMessage('Failed to update property');
        setErrorDialogOpen(true);
      }
    };
  
    const handleDelete = async (propertyId) => {
      const repairToDelete = properties.find((property) => property.id === propertyId);
      if (repairToDelete && repairToDelete.repairStatus !== 'DEFAULT_PENDING') {
        setErrorDialogMessage("You cannot delete this property");
        setErrorDialogOpen(true);
        console.log("Repair with status", repairToDelete.repairStatus, "cannot be deleted")
        return;
      }
  
      try {
        const responseStatus = await deleteProperties(propertyOwnerId, propertyId);
        if (responseStatus === 200) {
          setProperties(properties.filter((property) => property.id !== propertyId));
          console.log("Repair with id:", propertyId, "deleted successfully");
          setSnackbarMessage("Repair deleted successfully");
          setSnackbarOpen(true);
          setSuccessDialogOpen(true);
        }
      } catch (error) {
        console.error("Failed to delete property", error);
        setErrorDialogMessage('Failed to delete property');
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
          <Typography variant="h4">Properties</Typography>
          <Box>
            <Button variant="contained" color="primary" onClick={() => setSearchByTin(true)}>
              Search by Date
            </Button>
            <Button variant="contained" color="primary" onClick={() => setSearchByDateRangeDialogOpen(true)} style={{ marginLeft: '8px' }}>
              Search by Date Range
            </Button>
            <Button variant="contained" color="primary" onClick={handleCreateRepair} style={{ marginLeft: '8px' }}>
              Create Repair
            </Button>
          </Box>
        </Box>
        <Box>
          {properties.length === 0 ? (
            <Typography variant="h6" align="center">
              No properties found.
            </Typography>
          ) : (
            properties.map((property) => (
              <Box
                key={property.id}
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
                  <Typography variant="subtitle1">{property.shortDescription}</Typography>
                  <Typography variant="body2" color="textSecondary">
                    {property.repairStatus}
                  </Typography>
                </Box>
                <Box>
                  <Button variant="contained" color="primary" onClick={() => handleShow(property.id)}>
                    Show
                  </Button>
                  <Button variant="contained" color="secondary" onClick={() => handleUpdate(property.id)} style={{ marginLeft: '8px' }}>
                    Update
                  </Button>
                  <Button variant="contained" color="error" onClick={() => handleDelete(property.id)} style={{ marginLeft: '8px' }}>
                    Delete
                  </Button>
                </Box>
              </Box>
            ))
          )}
        </Box>
        {/* <UpdateRepairDialog
          open={updateDialogOpen}
          onClose={handleCloseUpdateDialog}
          property={selectedProperty}
          onChange={setSelectProperty}
          onSubmit={handleUpdateSubmit}
        />
        <ErrorDialog
          open={errorDialogOpen}
          message={errorDialogMessage}
          onClose={handleCloseErrorDialog}
        />
        <SearchByDateDialog
          open={searchByTinDialogOpen}
          onClose={() => setSearchByTin(false)}
          onSearch={handleSearchByDate}
        />
        <SearchByDateRangeDialog
          open={searchByDateRangeDialogOpen}
          onClose={() => setSearchByDateRangeDialogOpen(false)}
          onSearch={handleSearchByArea}
        />
        <Snackbar
          open={snackbarOpen}
          autoHideDuration={6000}
          onClose={handleSnackbarClose}
          message={snackbarMessage}
        /> */}
      </Container>
    );
  };
  
  export default ShowProperties;
  