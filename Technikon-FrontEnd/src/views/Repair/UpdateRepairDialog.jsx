import React, { useState } from 'react';
import { Dialog, DialogTitle, DialogContent, DialogContentText, DialogActions, Button } from '@mui/material';
import RepairForm from './RepairForm';
import { validateCost, validateDate } from './validationUtils';

const UpdateRepairDialog = ({ open, onClose, repair, onChange, onSubmit }) => {
  const [errors, setErrors] = useState({});

  const handleSubmit = () => {
    const isCostValid = validateCost(repair.cost, setErrors);
    const isDateValid = validateDate(repair.dateOfRepair, setErrors);

    if (!isCostValid || !isDateValid) {
      return;
    }

    onSubmit();
  };

  return (
    <Dialog open={open} onClose={onClose}>
      <DialogTitle>Update Repair</DialogTitle>
      <DialogContent>
        <DialogContentText>Update the repair details below:</DialogContentText>
        <RepairForm
          formData={repair} // Use repair object as formData
          setFormData={onChange}
          errors={errors}
          handleSubmit={handleSubmit}
        />
      </DialogContent>
      <DialogActions>
        <Button onClick={onClose} color="primary">Cancel</Button>
        <Button onClick={handleSubmit} color="primary">Update</Button>
      </DialogActions>
    </Dialog>
  );
};

export default UpdateRepairDialog;

