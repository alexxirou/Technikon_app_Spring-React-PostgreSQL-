// src/components/Signup.js
import { useEffect, useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import { Box } from '@mui/material';
import { PATHS } from '../../lib/constants';
import SignupForm from './SignupForm';
import SignupDialog from './SignupDialog';

const Signup = () => {
  const [formData, setFormData] = useState({
    tin: '',
    username: '',
    password: '',
    email: '',
    name: '',
    surname: '',
    address: '',
    phoneNumber: ''
  });
  const [error, setError] = useState('');
  const [isSubmitting, setIsSubmitting] = useState(false);
  const [success, setSuccess] = useState(false);
  const navigate = useNavigate();

  useEffect(() => {
    const isSubmittingStored = localStorage.getItem('isSubmitting');
    if (isSubmittingStored) {
      setIsSubmitting(JSON.parse(isSubmittingStored));
    }
  }, []);

  const handleChange = (field, value) => {
    setFormData(prevFormData => ({
      ...prevFormData,
      [field]: value
    }));
  };

  const validateTin = (tin) => /^[a-zA-Z0-9]{9,}$/.test(tin);
  const validatePassword = (password) => /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[\W_]).{8,}$/.test(password);
  const validateEmail = (email) => /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email);
  const validatePhoneNumber = (phoneNumber) => /^\+[1-9]\d{1,14}$/.test(phoneNumber);

  const isFormValid = () => {
    const { tin, username, password, email, phoneNumber } = formData;
    return (
      validateTin(tin) &&
      username.length >= 5 &&
      validatePassword(password) &&
      validateEmail(email) &&
      validatePhoneNumber(phoneNumber)
    );
  };

  const handleSubmit = async (event) => {
    event.preventDefault();

    try {
      setIsSubmitting(true);
      localStorage.setItem('isSubmitting', true);

      const { tin, username, password, email, name, surname, address, phoneNumber } = formData;

      if (!validateTin(tin)) throw new Error('Invalid TIN format.');
      if (username.length < 5) throw new Error('Username must be at least 5 characters long.');
      if (!validatePassword(password)) throw new Error('Password does not meet the required criteria.');
      if (!validateEmail(email)) throw new Error('Invalid email format.');
      if (!validatePhoneNumber(phoneNumber)) throw new Error('Invalid phone number format.');

      const response = await axios.post('http://localhost:5001/auth/signup', {
        tin,
        name,
        surname,
        email,
        username,
        password,
        address,
        phoneNumber,
      }, {
        withCredentials: true,
      });

      if (response.status === 201) {
        setSuccess(true);
        setError('');
        setTimeout(() => {
          navigate(PATHS.LOGIN);
        }, 3000);
      } else {
        const errorMessage = response.data || 'Signup failed: Invalid response';
        throw new Error(errorMessage);
      }
    } catch (error) {
      console.error('Error:', error);
      if (error.response && error.response.data) {
        setError(error.response.data);
      } else if (error.request) {
        setError('No response received from the server.');
      } else {
        setError(error.message || 'An unexpected error occurred.');
      }
    } finally {
      setIsSubmitting(false);
      localStorage.setItem('isSubmitting', false);
    }
  };

  return (
    <Box display="flex" justifyContent="center" alignItems="center" minHeight="100vh">
      <Box width="100%" maxWidth="500px" padding="20px" boxShadow={3} borderRadius={2}>
        <h2>Signup</h2>
        {error && <p className="error-message">{error}</p>}
        <SignupForm
          formData={formData}
          handleChange={handleChange}
          handleSubmit={handleSubmit}
          isFormValid={isFormValid}
          isSubmitting={isSubmitting}
        />
      </Box>

      <SignupDialog success={success} navigate={navigate} PATHS={PATHS} />
    </Box>
  );
};

export default Signup;
