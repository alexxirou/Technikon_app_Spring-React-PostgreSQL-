import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { Box, CircularProgress, Button, Typography, Container } from '@mui/material';
import api from '../../api/Api';

import { useAuth } from '../../hooks/useAuth';

const ShowRepairs = () => {
  const [repairs, setRepairs] = useState([]);
  const [loading, setLoading] = useState(true);
  const { authData } = useAuth();
  const navigate = useNavigate();
  const propertyOwnerId =authData.userId;

  useEffect(() => {
    const fetchRepairs = async () => {
      try {
        const response = await api.get('/api/property-repairs/all-by-owner/'+ propertyOwnerId);
        console.log(propertyOwnerId);
        setRepairs(response.data);
      } catch (error) {
        console.error("Failed to fetch repairs", error);
      } finally {
        setLoading(false);
      }
    };

    fetchRepairs();
  }, []);

  const handleShow = (propertyOwnerId, repairId) => {
    navigate(`/${propertyOwnerId}/${repairId}`);
  };

  const handleUpdate = (repairId) => {
    navigate(`/update/${repairId}`);
  };

  const handleDelete = async (repairId) => {
    try {
      await api.delete(`/api/property-repairs/${repairId}/delete`);
      setRepairs(repairs.filter(repair => repair.id !== repairId));
    } catch (error) {
      console.error("Failed to delete repair", error);
    }
  };

  if (loading) {
    return (
      <Box display="flex" justifyContent="center" alignItems="center" height="100vh">
        <CircularProgress />
      </Box>
    );
  }

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
                <Button variant="contained" color="primary" onClick={() => handleShow(propertyOwnerId, repair.id)}>
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
