import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom'; 
import { Dialog, DialogTitle, DialogContent, DialogContentText, DialogActions, Button, TextField } from '@mui/material';
import { PATHS } from '../../lib/constants'; 

const UpdatePropertyDialog = ({ open, onClose, property, onChange, onSubmit }) => {
  const [errors, setErrors] = useState({});
  const navigate = useNavigate(); 

  const setPropertyData = (updatedData) => {
    // Merge the updated data with the existing property data
    onChange({ ...property, ...updatedData });
  };

  const handleSubmit = () => {
    // Perform validation or other necessary checks before submitting
    onSubmit();
  };

  const handleCancel = () => {
    onClose();
    navigate(PATHS.SHOW_PROPERTIES); // Navigate to the properties page on cancel
  };

  // Provide default values from the property object or use empty string as default
  const defaultProperty = {
    constructionYear: property?.constructionYear || '',
    address: property?.address || '',
    latitude: property?.latitude || '',
    longitude: property?.longitude || '',
    picture: property?.picture || '',
    propertyType: property?.propertyType || ''
  };

  const propertyData = property || defaultProperty;

  return (
    <Dialog open={open} onClose={handleCancel}>
      <DialogTitle>Update Property</DialogTitle>
      <DialogContent>
        <DialogContentText>Update the property details below:</DialogContentText>
        {/* Form fields for updating property details */}
        <TextField
          label="Construction Year"
          value={propertyData.constructionYear}
          onChange={(e) => setPropertyData({ constructionYear: e.target.value })}
          error={errors.constructionYear}
          helperText={errors.constructionYear && 'Invalid construction year'}
        />
        <TextField
          label="Address"
          value={propertyData.address}
          onChange={(e) => setPropertyData({ address: e.target.value })}
          error={errors.address}
          helperText={errors.address && 'Invalid address'}
        />
        <TextField
          label="Latitude"
          value={propertyData.latitude}
          onChange={(e) => setPropertyData({ latitude: e.target.value })}
          error={errors.latitude}
          helperText={errors.latitude && 'Invalid latitude'}
        />
        <TextField
          label="Longitude"
          value={propertyData.longitude}
          onChange={(e) => setPropertyData({ longitude: e.target.value })}
          error={errors.longitude}
          helperText={errors.longitude && 'Invalid longitude'}
        />
        <TextField
          label="Picture"
          value={propertyData.picture}
          onChange={(e) => setPropertyData({ picture: e.target.value })}
          error={errors.picture}
          helperText={errors.picture && 'Invalid picture URL'}
        />
        <TextField
          label="Property Type"
          value={propertyData.propertyType}
          onChange={(e) => setPropertyData({ propertyType: e.target.value })}
          error={errors.propertyType}
          helperText={errors.propertyType && 'Invalid property type'}
        />
      </DialogContent>
      <DialogActions>
        <Button onClick={handleCancel} color="primary">Cancel</Button>
        <Button onClick={handleSubmit} color="primary">Update</Button>
      </DialogActions>
    </Dialog>
  );
};

export default UpdatePropertyDialog;
