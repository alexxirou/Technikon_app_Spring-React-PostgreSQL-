// src/components/UpdateOwner.js
import { useState } from 'react';
import { useAuth } from '../../hooks/useAuth';
import api from '../../api/Api';
import Modal from '@mui/material/Modal';
import { Button } from '@mui/material';
import UpdateForm from './UpdateForm';

const UpdateOwner = ({ ownerDetails, setOwnerDetails }) => {
  const { authData } = useAuth();
  const [errors, setErrors] = useState({});
  const tin = authData?.userTin;
  const [success, setSuccess] = useState(false);
  const [password, setPassword] = useState('');
  const [email, setEmail] = useState(ownerDetails?.email);
  const [address, setAddress] = useState(ownerDetails?.address);
  const [loading, setLoading] = useState(false);
  const [open, setOpen] = useState(false);

  const handleChange = (field, value) => {
    if (field === 'email') setEmail(value);
    if (field === 'address') setAddress(value);
    if (field === 'password') setPassword(value);
  };

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
      const requestBody = {
        email: email.trim() !== '' ? email : null,
        address: address.trim() !== '' ? address : null,
        password: password.trim() !== '' ? password : null,
      };

      const response = await api.put(`/api/propertyOwners/${tin}`, requestBody);
      if (response.status === 202) {
        setSuccess(true);
        setOpen(false);
        setOwnerDetails((prevOwnerDetails) => ({
          ...prevOwnerDetails,
          email: requestBody.email !== null ? requestBody.email : prevOwnerDetails.email,
          address: requestBody.address !== null ? requestBody.address : prevOwnerDetails.address,
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

  const isValidEmail = (email) => /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email);
  const isValidPassword = (password) => /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[\W_]).{8,}$/.test(password);

  return (
    <>
      <Modal open={open} onClose={() => setOpen(false)}>
        <UpdateForm
          email={email}
          address={address}
          password={password}
          errors={errors}
          loading={loading}
          handleChange={handleChange}
          handleSubmit={handleSubmit}
          handleClose={() => setOpen(false)}
          success={success}
        />
      </Modal>
      <Button onClick={() => setOpen(true)} variant="contained" color="primary">
        Update Owner
      </Button>
    </>
  );
};

export default UpdateOwner;
