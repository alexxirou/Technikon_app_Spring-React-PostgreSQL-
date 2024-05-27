import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { Box, CircularProgress, Button, Typography, Container, Snackbar } from '@mui/material';
import { fetchProperty, deleteProperties, getAllProperties } from './ApiPropertyService';
import { PATHS } from '../../lib/constants';
import { useAuth } from '../../hooks/useAuth';

const ShowProperties = () => {
  const [properties, setProperties] = useState([]);
  const [loading, setLoading] = useState(true);
  const [snackbarOpen, setSnackbarOpen] = useState(false);
  const [snackbarMessage, setSnackbarMessage] = useState('');
  const { authData } = useAuth();
  const navigate = useNavigate();
  const authorities = authData?.authorities;
  const propertyOwnerId = authData?.userId;

  useEffect(() => {
    if (!propertyOwnerId) {
      return;
    }

    const fetchAndSetProperties = async () => {
      try {
        let properties
        if (authorities && authorities.includes('ROLE_USER')) {
          properties = await fetchProperty(propertyOwnerId);
        }
        else {
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

    fetchAndSetProperties();
  }, [propertyOwnerId]);

  const handleCreateProperty = () => {
    navigate(PATHS.CREATE_PROPERTY);
  };

  const handleShowPropertyDetails = (propertyId) => {
    navigate(PATHS.PROPERTY_DETAILS(propertyOwnerId, propertyId));
  };

  const handleDeleteProperty = async (propertyId) => {
    try {
      const responseStatus = await deleteProperties(propertyOwnerId, propertyId);
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

  const handleSnackbarClose = () => {
    setSnackbarOpen(false);
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
    <Container maxWidth="lg">
      <Box mt={2} mb={2} display="flex" justifyContent="space-between" alignItems="center">
        <Typography variant="h4">Properties</Typography>
        <Button variant="contained" color="primary" onClick={handleCreateProperty}>
          Create Property
        </Button>
      </Box>
      <Box display="flex" flexDirection="column">
        {properties.length === 0 ? (
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

              <Box display="flex" flexDirection="row">
                <Typography variant="subtitle1" style={{ marginRight: '16px' }}>Address: {property.address}</Typography>
                <Typography variant="subtitle1"  style={{ marginRight: '16px' }}>Construction Year: {property.constructionYear}</Typography>
                <Typography variant="subtitle1" >Tin: {property.tin}</Typography>
              </Box>


              <Box display="flex">
                <Button variant="contained" color="primary" onClick={() => handleShowPropertyDetails(property.id)}>
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
    </Container>
  );
};

export default ShowProperties;