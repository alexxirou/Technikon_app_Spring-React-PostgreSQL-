import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { Container, Box } from '@mui/material';
import { useAuth } from '../../hooks/useAuth';
import { PATHS } from '../../lib/constants';
import SuccessDialog from './SuccessDialog';
import { createRepair } from './apiRepairService';
import { validateCost, validateDate } from './validationUtils';
import CreateForm from './CreateForm';

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

    const isCostValid = validateCost(formData.cost, setErrors);
    const isDateValid = validateDate(formData.dateOfRepair, setErrors);

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
      const response = await createRepair(authData.userId, repairData);
      console.log("Created Repair:", response); 
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

  const handleCancel = () => {
    handleGoToRepairs();
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
        <CreateForm
          formData={formData}
          setFormData={setFormData}
          errors={errors}
          handleSubmit={handleSubmit}
          handleCancel={handleCancel}
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
