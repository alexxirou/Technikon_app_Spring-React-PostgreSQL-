import { useState} from 'react';
import { useAuth } from '../../hooks/useAuth';

import api from '../../api/Api';

import Modal from '@mui/material/Modal';
import { Box, Typography, TextField, Button, CircularProgress } from '@mui/material';

const UpdateOwner = ({ ownerDetails, setOwnerDetails }) => {
  const { authData } = useAuth();
  const [errors, setErrors] = useState({});
  const tin = authData?.userTin;
  const [success, setSuccess] = useState(false);
  const [password, setPassword] = useState('');
  const [email, setEmail] = useState(ownerDetails?.email );
  const [address, setAddress] = useState(ownerDetails?.address );
  
  const [loading, setLoading] = useState(false);

  const [open, setOpen] = useState(false);



  const handleSubmit = async () => {
    const newErrors = {};
    if (email && !isValidEmail(email)) {
      newErrors.email = 'Invalid email format';
    }
    if (address && address.length < 5) {
      newErrors.address = 'Address should be at least 5 characters long';
    }
    if (password && !isValidPassword(password)) {
      newErrors.password = 'Password should be at least 8 characters long and contain at least one uppercase letter, one lowercase letter, one digit, and one special character.';
    }

    setErrors(newErrors);

    if (Object.keys(newErrors).length > 0) {
      return;
    }

    setLoading(true);


      
      try {
        // Check if email, address, and password are empty, and set them to null if they are
        const requestBody = {
          email: email.trim() !== '' ? email : null,
          address: address.trim() !== '' ? address : null,
          password: password.trim() !== '' ? password : null
          
        };
       
      
        const response = await api.put(`/api/propertyOwners/${tin}`, requestBody);
        if (response.status === 202) {
          setSuccess(true);
          setOpen(false); 
          setOwnerDetails(prevOwnerDetails => ({
            ...prevOwnerDetails,
            email: requestBody.email !== null ? requestBody.email : prevOwnerDetails.email,
            address: requestBody.address !== null ? requestBody.address : prevOwnerDetails.address,
            // Assuming password is always present in the request, so no need to check for null
          }));
        
      } else {
        throw new Error(response.data);
      }
    } catch (error) {
      console.error('Error:', error);
      if (error.response) {
        const errorMessage = error.response.data;
        setErrors(errorMessage);
      } else if (error.request) {
        setErrors('No response received from the server');
      } else {
        setErrors('An unexpected error occurred');
      }
    } finally {
      setLoading(false);
    }
  };

  const isValidEmail = (email) => {
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return emailRegex.test(email);
  };

  const isValidPassword = (password) => {
    const pattern = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[\W_]).{8,}$/;
    return pattern.test(password);
  };

  return (
    <>
      <Modal open={open} onClose={() => setOpen(false)}>
        <Box sx={{
          position: 'absolute',
          top: '50%',
          left: '50%',
          transform: 'translate(-50%, -50%)',
          width: 400,
          bgcolor: 'background.paper',
          border: '2px solid #000',
          boxShadow: 24,
          p: 4,
        }}>
          <Typography variant="h5">Update Owner</Typography>
          {success && (
            <Typography variant="body1" className="success-dialog">
              Owner details updated successfully. Redirecting...
            </Typography>
          )}
          <form>
            <TextField
              fullWidth
              margin="normal"
              id="email"
              label="Email Address"
              type="email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              error={!!errors.email}
              helperText={errors.email}
            />
            <TextField
              fullWidth
              margin="normal"
              id="address"
              label="Address"
              type="text"
              value={address}
              onChange={(e) => setAddress(e.target.value)}
              error={!!errors.address}
              helperText={errors.address}
            />
            <TextField
              fullWidth
              margin="normal"
              id="password"
              label="Password"
              type="password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              error={!!errors.password}
              helperText={errors.password}
            />
            {loading ? (
              <CircularProgress />
            ) : (
              <>
                <Button onClick={handleSubmit} variant="contained">Update Owner</Button>
                <Button onClick={() => setOpen(false)} variant="contained">Cancel</Button>
              </>
            )}
          </form>
        </Box>
      </Modal>
      <Button onClick={() => setOpen(true)} variant="contained" color="primary">
        Update Owner
      </Button>
    </>
  );
};

export default UpdateOwner;
