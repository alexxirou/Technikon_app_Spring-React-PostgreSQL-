import React from 'react';
import { Container, Box, Typography } from '@mui/material';
import { useAuth } from '../hooks/useAuth';

const Owner = () => {
  const { authData } = useAuth();

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
      </Box>
    </Container>
  );
};

export default Owner;

