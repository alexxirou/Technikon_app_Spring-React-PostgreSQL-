import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { Box, CircularProgress, Button, Typography, Container, Snackbar } from '@mui/material';
import { fetchProperty, deleteProperties, getAllProperties, updateProperties, createProperty } from './ApiPropertyService'; // Import createProperty function
import { PATHS, ROLES } from '../../lib/constants';
import { useAuth } from '../../hooks/useAuth';
import UpdatePropertyDialog from './UpdatePropertyDIalog';
import CreatePropertyDialog from './CreatePropertyDialog'; // Import the CreatePropertyDialog component

const ShowProperties = () => {
  const [properties, setProperties] = useState([]);
  const [loading, setLoading] = useState(true);
  const [snackbarOpen, setSnackbarOpen] = useState(false);
  const [snackbarMessage, setSnackbarMessage] = useState('');
  const [isUpdateDialogOpen, setIsUpdateDialogOpen] = useState(false);
  const [selectedProperty, setSelectedProperty] = useState(null);
  const [isCreateDialogOpen, setIsCreateDialogOpen] = useState(false); // State for controlling the visibility of the CreatePropertyDialog
  const [selectedPropertyId, setSelectedPropertyId] = useState(null); 
  const { authData } = useAuth();
  const navigate = useNavigate();
  const authorities = authData?.authorities;
  const propertyOwnerId = authData?.userId;

  const fetchAndSetProperties = async () => {
    try {
      let properties;
      if (authorities && authorities.includes('ROLE_USER')) {
        properties = await fetchProperty(propertyOwnerId);
      } else {
        const response = await getAllProperties();
        properties = response.data;
      }
      setProperties(properties);
    } catch (error) {
      console.error("Failed to fetch properties", error);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    if (!propertyOwnerId) {
      return;
    }

    fetchAndSetProperties();
  }, [propertyOwnerId]);

  const handleCreateProperty = () => {
    setIsCreateDialogOpen(true); // Open the CreatePropertyDialog
  };

  const handleCloseCreateDialog = () => {
    setIsCreateDialogOpen(false);
    fetchAndSetProperties(); 
  };

  const handleShowPropertyDetails = (Id, pId) => {

    navigate(PATHS.PROPERTY_DETAILS(Id, pId));
  };

  const handleDeleteProperty = async (propertyId) => {
    try {
      const responseStatus = await deleteProperties(propertyId);
      if (responseStatus === 200) {
        setProperties(properties.filter((property) => property.id !== propertyId));
        console.log("Property with id:", propertyId, "deleted successfully");
        setSnackbarMessage("Property deleted successfully");
        setSnackbarOpen(true);
      }
    } catch (error) {
      console.error("Failed to delete property", error);
      setSnackbarMessage("Failed to delete property");
      setSnackbarOpen(true);
    }
  };

  const handleUpdateProperty = (propertyId) => {
    const propertyToUpdate = properties.find(property => property.id === propertyId);
    setSelectedProperty(propertyToUpdate);
    setIsUpdateDialogOpen(true);
  };

  const handleSnackbarClose = () => {
    setSnackbarOpen(false);
  };

  const handleSubmitUpdateProperty = async () => {
    try {
      const response = await updateProperties(selectedProperty.id, selectedProperty);
      console.log("Property updated successfully", response);
      setIsUpdateDialogOpen(false);
      setSnackbarMessage("Property updated successfully");
      setSnackbarOpen(true);
      fetchAndSetProperties(); // Reload properties after update
    } catch (error) {
      console.error("Failed to update property", error);
      setSnackbarMessage("Failed to update property");
      setSnackbarOpen(true);
    }
  };
  

  return (
    <Container maxWidth="md">
      <Box mt={2} mb={2} display="flex" justifyContent="space-between" alignItems="center">
        <Typography variant="h4">Properties</Typography>
        {authData && authData.authorities.includes(ROLES.USER) && (
          <Button variant="contained" color="primary" onClick={handleCreateProperty}>
            Create Property
          </Button>
        )}
      </Box>
      <Box display="flex" flexDirection="column">
        {loading ? (
          <Box display="flex" justifyContent="center" alignItems="center" height="100vh">
            <CircularProgress />
          </Box>
        ) : properties.length === 0 ? (
          <Typography variant="h6" align="center">
            No properties found.
          </Typography>
        ) : (
          properties.map((property) => (
            <Box
              key={property.id}
              display="flex"
              flexDirection="row"
              justifyContent="space-between"
              alignItems="center"
              p={2}
              mb={2}
              border={1}
              borderRadius={8}
              borderColor="grey.300"
            >
              <Box>
                <Typography variant="subtitle1" style={{ marginRight: '10px' }}>Owner Tin: {property.propertyOwner.tin}</Typography>
                <Typography variant="subtitle1" style={{ marginRight: '10px' }}>Property Tin: {property.tin}</Typography>
                <Typography variant="subtitle1" style={{ marginRight: '10px' }}>Address: {property.address}</Typography>
              </Box>
              <Box display="flex">
                <Button variant="contained" color="primary" onClick={() => handleShowPropertyDetails(property.propertyOwner.id, property.id)}>
                  Show Details
                </Button>
                <Button variant="contained" color="error" onClick={() => handleDeleteProperty(property.id)} style={{ marginLeft: '8px' }}>
                  Delete
                </Button>
                <Button variant="contained" color="secondary" onClick={() => handleUpdateProperty(property.id)} style={{ marginLeft: '8px' }}>
                  Update
                </Button>
              </Box>
           
            </Box>
          ))
        )}
      </Box>
      <Snackbar
        open={snackbarOpen}
        autoHideDuration={6000}
        onClose={handleSnackbarClose}
        message={snackbarMessage}
      />
      <UpdatePropertyDialog
        open={isUpdateDialogOpen}
        onClose={() => setIsUpdateDialogOpen(false)}
        property={selectedProperty}
        onChange={setSelectedProperty}
        onSubmit={handleSubmitUpdateProperty} // Implement the update submission function
      />
      <CreatePropertyDialog
        open={isCreateDialogOpen}
        onClose={handleCloseCreateDialog}
        onCreate={() => {
          handleCloseCreateDialog(); // Close the dialog
          fetchAndSetProperties(); // Reload properties after creation
        }}
      />
    </Container>
  );
};

export default ShowProperties;
