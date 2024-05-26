import  { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { jwtDecode } from "jwt-decode";
import { useAuth } from '../../hooks/useAuth';
import axios from 'axios';
import { Container, TextField, Button, Typography, Alert, CircularProgress } from '@mui/material';
import { PATHS, ROLES } from '../../lib/constants';


const Login = () => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');
  const navigate = useNavigate();
  const { setAuthData} = useAuth();

  // Load isSubmitting state from localStorage or default to false
  const [isSubmitting, setIsSubmitting] = useState(() => {
    return localStorage.getItem('isSubmitting')? localStorage.getItem('isSubmitting') === 'true' : false;
  });


  useEffect(() => {
    // Load isSubmitting state from localStorage or default to false
    const storedValue = localStorage.getItem('isSubmitting');
    setIsSubmitting(storedValue ? storedValue === 'true' : false);
    
    // Set submitting state to false in localStorage when component unmounts
    return () => {
      localStorage.setItem('isSubmitting', 'false');
    };
  }, []);
  
  // Use useEffect to execute logic dependent on authData



  const  handleAuthentication = (tin, id, username, authorities, exp) => {
     setAuthData({ userId:id, userTin:tin, username:username, authorities: authorities, expDate:exp });
   

    if (authorities.includes(ROLES.ADMIN)) {
      navigate(PATHS.ADMIN);
    } else if (authorities.includes(ROLES.USER)) {
      navigate(PATHS.OWNER(tin));
    } else {
      navigate(PATHS.HOME);
    }
  };

  const handleSubmit = async (event) => {
    event.preventDefault();
  
    // Prevent multiple submissions
    if (isSubmitting) {
      return;
    }
  
    try {
      setIsSubmitting(true);
  
      const response = await axios.post('http://localhost:5001/auth/login', {
        username,
        password,
      }, {
        withCredentials: true,
      });
  
      if (response.status === 200) {
        const data = await response.data;
        
        if (data.token) {
       
          localStorage.setItem('token', data.token);
          const decodedToken = jwtDecode(data.token);
  
        
          const { tin, id, username, authorities: authoritiesArray, exp } = decodedToken;
          const authorities = authoritiesArray.map(authority => authority.authority);
          handleAuthentication(tin, id, username, authorities, exp);
        } 
      } else {
        const errorMessage = response.data.message || 'Login failed: Invalid response';
        throw new Error(errorMessage);
      }
    } catch (error) {
      console.error('Error:', error);
      if (error.response) {
        const errorMessage = error.response.data || 'Login failed: Invalid response';
        setError(errorMessage);
      } else if (error.request) {
        setError('No response received from the server');
      } else {
        setError('An unexpected error occurred');
      }
    } finally {
      setIsSubmitting(false);
    }
  };

  return (
    <Container maxWidth="xs">
      <div style={{ marginTop: '20px', textAlign: 'center' }}>
        <Typography variant="h4">Login</Typography>
        {error && <Alert severity="error" style={{ margin: '10px 0' }}>{error}</Alert>}
        <form onSubmit={handleSubmit} style={{ marginTop: '20px' }}>
          <div className="form-group">
            <TextField
              label="Username"
              variant="outlined"
              fullWidth
              value={username}
              onChange={(e) => setUsername(e.target.value)}
              required
              margin="normal"
            />
          </div>
          <div className="form-group">
            <TextField
              label="Password"
              variant="outlined"
              type="password"
              fullWidth
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              required
              margin="normal"
            />
          </div>
          <Button
            type="submit"
            variant="contained"
            color="primary"
            disabled={isSubmitting}
            fullWidth
            style={{ marginTop: '20px' }}
          >
            {isSubmitting ? <CircularProgress size={24} /> : 'Login'}
          </Button>
        </form>
      </div>
    </Container>
  );
};

export default Login;