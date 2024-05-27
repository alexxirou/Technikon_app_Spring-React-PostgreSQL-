import React, { useEffect, useState, useRef } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { Box, CircularProgress, Typography, Container, Button } from '@mui/material';
import api from '../../api/Api';
import { useAuth } from '../../hooks/useAuth';
import { PATHS } from '../../lib/constants';

const RepairDetails = () => {
  const { repairId } = useParams();
  const [repair, setRepair] = useState(null);
  const [loading, setLoading] = useState(true);
  const navigate = useNavigate();
  const { authData } = useAuth();
  const fetchInitiated = useRef(false);

  useEffect(() => {
    if (!authData || fetchInitiated.current) {
      return;
    }
    fetchInitiated.current = true;
    const propertyOwnerId = authData.userId;
    const fetchRepair = async () => {
      try {
        const response = await api.get(`/api/property-repairs/${propertyOwnerId}/${repairId}`);
        setRepair(response.data);
        console.log("Repair details:", response.data);
      } catch (error) {
        console.error("Failed to fetch repair details", error);
      } finally {
        setLoading(false);
      }
    };
    fetchRepair();
  }, [repairId, authData]);

  const formatDate = (dateString) => {
    const date = new Date(dateString);
    const day = date.getDate().toString().padStart(2, '0');
    const month = (date.getMonth() + 1).toString().padStart(2, '0');
    const year = date.getFullYear();
    return `${day}-${month}-${year}`;
  };

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

  const handleBackToRepairs = () => {
    if (authData) {
      navigate(PATHS.SHOW_REPAIRS(authData.userId));
    }
  };

  return (
    <Container maxWidth="md">
      <Box mt={2} p={2} border={1} borderRadius={8} borderColor="grey.300">
        <Typography variant="h5" gutterBottom>
          Repair Details
        </Typography>
        <Typography variant="subtitle1">
          Short Description: {repair.shortDescription}
        </Typography>
        <Typography variant="subtitle1">
          Tin: {repair.propertyTin}
        </Typography>
        <Typography variant="subtitle1">
          Address: {repair.address}
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
          Date of Repair: {formatDate(repair.dateOfRepair)}
        </Typography>
        <Typography variant="body2" color="textSecondary">
          Cost: {repair.cost} €
        </Typography>
        <Box mt={2}>
          <Button variant="contained" color="primary" onClick={handleBackToRepairs}>
            Back to Repairs
          </Button>
        </Box>
      </Box>
    </Container>
  );
};

export default RepairDetails;
