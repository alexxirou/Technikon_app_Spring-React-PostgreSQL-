// Inside CreateRepair.js

import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { Container, Box } from '@mui/material';
import api from '../../api/Api';
import { useAuth } from '../../hooks/useAuth';
import { PATHS } from '../../lib/constants';
import RepairForm from './RepairForm';
import SuccessDialog from './SuccessDialog';

const CreateRepair = () => {
  const navigate = useNavigate();
  const [formData, setFormData] = useState({
    dateOfRepair: '',
    shortDescription: '',
    repairType: '',
    repairStatus: '',
    cost: '',
    longDescription: ''
  });
  const [errors, setErrors] = useState({});
  const [success, setSuccess] = useState(false);
  const { authData } = useAuth();

  const handleSubmit = async (event) => {
    event.preventDefault();

    const isCostValid = validateCost(formData.cost);
    const isDateValid = validateDate(formData.dateOfRepair);

    if (!isCostValid || !isDateValid) {
      return;
    }

    const propertyOwnerId = authData.userId;
    const propertyId = 1;
    const repairData = {
      id: 0,
      propertyOwnerId,
      propertyId,
      ...formData,
    };

    try {
      const response = await api.post('/api/property-repairs/create', repairData);
      console.log("Created Repair:", response.data); // Log the created repair data
      setSuccess(true); // Show the success dialog
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
    }
  };

  const handleGoToRepairs = () => {
    if (authData) {
      navigate(PATHS.SHOW_REPAIRS(authData.userId));
    } else {
      console.error("User data not available");
    }
  };

  const validateCost = (cost) => {
    if (cost <= 0) {
      setErrors((prevErrors) => ({
        ...prevErrors,
        cost: 'Insert a valid cost'
      }));
      return false;
    } else {
      setErrors((prevErrors) => {
        const { cost, ...rest } = prevErrors;
        return rest;
      });
      return true;
    }
  };

  const validateDate = (date) => {
    const today = new Date().toISOString().split('T')[0];
    if (date < today) {
      setErrors((prevErrors) => ({
        ...prevErrors,
        dateOfRepair: 'Date of repair cannot be in the past'
      }));
      return false;
    } else {
      setErrors((prevErrors) => {
        const { dateOfRepair, ...rest } = prevErrors;
        return rest;
      });
      return true;
    }
  };

  return (
    <Container maxWidth="sm">
      <Box
        display="flex"
        flexDirection="column"
        alignItems="center"
        justifyContent="center"
        minHeight="100vh"
      >
        <RepairForm
          formData={formData}
          setFormData={setFormData}
          errors={errors}
          handleSubmit={handleSubmit}
        />
        <SuccessDialog
          success={success}
          handleClose={() => setSuccess(false)}
          handleGoToRepairs={handleGoToRepairs} // Pass handleGoToRepairs as a prop
        />
      </Box>
    </Container>
  );
};

export default CreateRepair;
