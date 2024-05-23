import React, { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { Box, CircularProgress, Typography, Container, Button } from '@mui/material';
import api from '../../api/Api';
import { useAuth } from '../../hooks/useAuth';

const RepairDetails = () => {
  const { repairId } = useParams();
  const [repair, setRepair] = useState(null);
  const [loading, setLoading] = useState(true);
  const navigate = useNavigate();
  const { authData } = useAuth();
  const propertyOwnerId = authData.userId;

  useEffect(() => {
    const fetchRepair = async () => {
      try {
        const response = await api.get(`/api/property-repairs/${propertyOwnerId}/${repairId}`);
        setRepair(response.data);
      } catch (error) {
        console.error("Failed to fetch repair details", error);
      } finally {
        setLoading(false);
      }
    };

    fetchRepair();
  }, [propertyOwnerId, repairId]);

  if (loading) {
    return (
      <Box display="flex" justifyContent="center" alignItems="center" height="100vh">
        <CircularProgress />
      </Box>
    );
  }

  if (!repair) {
    return (
      <Container maxWidth="md">
        <Typography variant="h6" align="center">
          Repair not found.
        </Typography>
      </Container>
    );
  }

  return (
    <Container maxWidth="md">
      <Box mt={2} p={2} border={1} borderRadius={8} borderColor="grey.300">
        <Typography variant="h5" gutterBottom>
          Repair Details
        </Typography>
        <Typography variant="subtitle1">
          Short Description: {repair.shortDescription}
        </Typography>
        <Typography variant="body2" color="textSecondary">
          Long Description: {repair.longDescription}
        </Typography>
        <Typography variant="body2" color="textSecondary">
          Repair Type: {repair.repairType}
        </Typography>
        <Typography variant="body2" color="textSecondary">
          Repair Status: {repair.repairStatus}
        </Typography>
        <Typography variant="body2" color="textSecondary">
          Date of Repair: {repair.dateOfRepair}
        </Typography>
        <Typography variant="body2" color="textSecondary">
          Cost: ${repair.cost}
        </Typography>
        <Box mt={2}>
          <Button variant="contained" color="primary" onClick={() => navigate('/repairs')}>
            Back to Repairs
          </Button>
        </Box>
      </Box>
    </Container>
  );
};

export default RepairDetails;
