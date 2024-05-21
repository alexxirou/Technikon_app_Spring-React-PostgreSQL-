import React from 'react';
import { Container, Typography, Button, Box, Paper } from '@mui/material';
import { useNavigate } from 'react-router-dom';

const HomePage = () => {
  const navigate = useNavigate();
  return (
    <Box
      display="flex"
      flexDirection="column"
      alignItems="center"
      justifyContent="flex-start"
      height="100vh"
      bgcolor="background.default"
      pt={8}
    >
      <Container maxWidth="sm">
        <Paper elevation={3} sx={{ p: 4, mt: 2, mb: 3 }}>
          <Typography variant="h2" align="center" gutterBottom>
            Welcome to Technikon
          </Typography>
          <Typography variant="body1" align="center" gutterBottom>
            Your one-stop solution for property management. Manage properties, connect with property owners, and handle property repairs seamlessly.
          </Typography>
        </Paper>
        <Button variant="contained" color="primary" sx={{ mt: 2 }}>
          Sign Up
        </Button>
        <Button 
          variant="outlined" 
          color="primary" 
          sx={{ mt: 2 }}
          onClick={() => navigate('/login')} // navigate to login page when clicked
        >
          Log In
        </Button>
      </Container>
    </Box>
  );
};

export default HomePage;
