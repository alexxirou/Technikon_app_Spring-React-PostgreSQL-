import React from 'react';
import { Container, Box, Typography, Button } from '@mui/material';
import { useNavigate } from 'react-router-dom';
import { useAuth } from '../hooks/useAuth';

const Owner = () => {
  const { authData } = useAuth();
  const navigate = useNavigate();

  console.log(authData);

  return (
    <Container maxWidth="sm">
      <Box
        display="flex"
        flexDirection="column"
        alignItems="center"
        justifyContent="center"
        minHeight="100vh"
        textAlign="center"
      >
        <Typography variant="h4" component="h1" gutterBottom>
          Owner
        </Typography>
        <Typography variant="body1" gutterBottom>
          User ID: {authData.userId}
        </Typography>
        <Typography variant="body1" gutterBottom>
          User TIN: {authData.userTin}
        </Typography>
        <Typography variant="body1" gutterBottom>
          Username: {authData.username}
        </Typography>
        <Typography variant="body1" gutterBottom>
          Authorities: {authData.authorities?.join(', ')}
        </Typography>
        <Button
          variant="contained"
          color="primary"
          onClick={() => navigate('/repair')}
          style={{ marginTop: '20px' }}
        >
          Go to repairs
        </Button>
      </Box>
    </Container>
  );
};

export default Owner;
