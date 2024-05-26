import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom'; 
import { Dialog, DialogTitle, DialogContent, DialogContentText, DialogActions, Button } from '@mui/material';
import RepairForm from './RepairForm';
import { validateCost, validateDate } from './validationUtils';
import { PATHS } from '../../lib/constants'; 

const UpdateRepairDialog = ({ open, onClose, repair, onChange, onSubmit }) => {
  const [errors, setErrors] = useState({});
  const navigate = useNavigate(); 

  const setFormData = (updatedData) => {
    // Merge the updated data with the existing repair data
    onChange({ ...repair, ...updatedData });
  };

  const handleSubmit = () => {
    const isCostValid = validateCost(repair.cost, setErrors);
    const isDateValid = validateDate(repair.dateOfRepair, setErrors);

    if (!isCostValid || !isDateValid) {
      return;
    }
    onSubmit();
  };

  const handleCancel = () => {
    onClose();
    navigate(PATHS.SHOW_REPAIRS);
  };

  // Provide default values to avoid null/undefined errors
  const defaultRepair = {
    dateOfRepair: '',
    shortDescription: '',
    repairType: '',
    repairStatus: '',
    cost: '',
    longDescription: ''
  };

  const repairData = repair || defaultRepair;

  return (
    <Dialog open={open} onClose={handleCancel}>
      <DialogTitle>Update Repair</DialogTitle>
      <DialogContent>
        <DialogContentText>Update the repair details below:</DialogContentText>
        <RepairForm
          formData={repairData}
          setFormData={setFormData} // Pass the setFormData function
          errors={errors}
          handleSubmit={handleSubmit}
          isUpdating={true} // Pass isUpdating prop if needed
        />
      </DialogContent>
      <DialogActions>
        <Button onClick={handleCancel} color="primary">Cancel</Button>
        <Button onClick={handleSubmit} color="primary">Update</Button>
      </DialogActions>
    </Dialog>
  );
};

export default UpdateRepairDialog;
