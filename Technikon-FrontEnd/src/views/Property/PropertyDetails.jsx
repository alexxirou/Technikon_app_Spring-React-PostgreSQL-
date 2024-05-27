import { Box, CircularProgress, Typography, Container, Button } from '@mui/material';
import { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { fetchPropertyDetails } from './ApiPropertyService';

const PropertyDetails = (Id, pId) => {
  const { propertyOwnerId, propertyId } = useParams(); // Use useParams to get propertyOwnerId and propertyId from the URL
  const [property, setProperty] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const navigate = useNavigate(); // Use useNavigate for navigation

  useEffect(() => {
    const getPropertyDetails = async () => {
      try {
        const data = await fetchPropertyDetails(propertyId);
        setProperty(data);
      } catch (err) {
        setError(err);
      } finally {
        setLoading(false);
      }
    };

    getPropertyDetails();
  }, [propertyId]); // Fetch property details whenever propertyId changes

  const handleBack = () => {
    navigate(`/property-repairs/${propertyOwnerId}`); // Navigate back to property repairs page
  };

  if (loading) {
    return (
      <Box display="flex" justifyContent="center" alignItems="center" height="100vh">
        <CircularProgress />
      </Box>
    );
  }

  if (error) {
    return (
      <Container maxWidth="md">
        <Typography variant="h6" align="center" color="error">
          Error: {error.message}
        </Typography>
      </Container>
    );
  }

  if (!property) {
    return (
      <Container maxWidth="md">
        <Typography variant="h6" align="center">
          Property not found.
        </Typography>
      </Container>
    );
  }

  return (
    <Container maxWidth="md">
      <Box mt={2} p={2} border={1} borderRadius={8} borderColor="grey.300">
        <Typography variant="h5" gutterBottom>
          Property Details
        </Typography>
        <Typography variant="subtitle1">
          ID: {property.id}
        </Typography>
        <Typography variant="subtitle1">
          TIN: {property.tin}
        </Typography>
        <Typography variant="subtitle1">
          Address: {property.address}
        </Typography>
        <Typography variant="subtitle1">
          Construction Year: {property.constructionYear}
        </Typography>
        <Typography variant="subtitle1">
          Latitude: {property.latitude}
        </Typography>
        <Typography variant="subtitle1">
          Longitude: {property.longitude}
        </Typography>
        <Typography variant="subtitle1">
          Property Type: {property.propertyType}
        </Typography>
        {property.picture && (
          <Box mt={2}>
            <img src={property.picture} alt="Property" style={{ maxWidth: '100%' }} />
          </Box>
        )}
      </Box>
      <Box mt={2}>
        <Button variant="contained" color="primary" onClick={handleBack}>
          Back to Property Repairs
        </Button>
      </Box>
    </Container>
  );
};

export default PropertyDetails;
