import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { Box, CircularProgress, Button, Typography, Container } from '@mui/material';
import api from '../../api/Api';
import { PATHS } from '../../lib/constants';
import { useAuth } from '../../hooks/useAuth';

const ShowRepairs = () => {
  const [repairs, setRepairs] = useState([]);
  const [loading, setLoading] = useState(true);
  const { authData } = useAuth();
  const navigate = useNavigate();

  // Effect to fetch repairs
  useEffect(() => {
    if (!authData) {
      return;
    }

    const propertyOwnerId = authData.userId;
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
  }, [authData]);

  if (!authData || loading) {
    return (
      <Box display="flex" justifyContent="center" alignItems="center" height="100vh">
        <CircularProgress />
      </Box>
    );
  }

  const propertyOwnerId = authData.userId;

  const handleShow = (repairId) => {
    navigate(PATHS.REPAIR_DETAILS.replace(':propertyOwnerId', propertyOwnerId).replace(':repairId', repairId));
  };

  const handleUpdate = (repairId) => {
    navigate(`/update/${repairId}`);
  };

  const handleDelete = async (repairId) => {
    try {
      await api.delete(`/${propertyOwnerId}/delete/${repairId}`);
      setRepairs(repairs.filter(repair => repair.id !== repairId));
    } catch (error) {
      console.error("Failed to delete repair", error);
    }
  };

  return (
    <Container maxWidth="md">
      <Box mt={2}>
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
    </Container>
  );
};

export default ShowRepairs;

