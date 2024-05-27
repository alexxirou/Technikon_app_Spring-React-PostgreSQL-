import { useEffect } from 'react';
import { Container, Typography, Button, Box, Paper } from '@mui/material';
import { useNavigate } from 'react-router-dom';
import { useAuth } from '../../hooks/useAuth';
import { PATHS } from '../../lib/constants';

const HomePage = () => {
  const navigate = useNavigate();
  const { authData } = useAuth();

  useEffect(() => {
    // Remove scrollbar from the document body
    document.body.style.overflow = 'hidden';

    // Cleanup effect on component unmount
    return () => {
      document.body.style.overflow = 'auto';
    };
  }, []); // Run only once on component mount

  return (
    <Box
      display="flex"
      flexDirection="column"
      alignItems="center"
      justifyContent="flex-start"
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
        {!authData ? (
          <>
            <Button 
              variant="contained" 
              color="primary" 
              sx={{ mt: 2 }} 
              onClick={() => navigate(PATHS.SIGNUP)}
            >
              Sign Up
            </Button>
            <Button 
              variant="outlined" 
              color="primary" 
              sx={{ mt: 2 }} 
              onClick={() => navigate(PATHS.LOGIN)}
            >
              Log In
            </Button>
          </>
        ) : null 
        }
      </Container>
    </Box>
  );
};

export default HomePage;
