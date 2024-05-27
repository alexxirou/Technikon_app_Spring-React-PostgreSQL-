import React, { useState } from 'react';
import { Dialog, DialogTitle, DialogContent, DialogActions, Button, TextField, MenuItem, Select, FormControl, InputLabel } from '@mui/material';
import { createProperty } from './ApiPropertyService'; // Import the function to create a property
import { useAuth } from '../../hooks/useAuth';

const CreatePropertyDialog = ({ open, onClose, onCreate }) => {
  const { authData } = useAuth();
  const userId = authData?.userId;

  const [formData, setFormData] = useState({
    id: 0,
    propertyOwnerId: userId,
    tin: '',
    address: '',
    constructionYear: '',
    latitude: '',
    longitude: '',
    propertyType: '',
    picture: ''
  });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  };

  const handleSubmit = async () => {
    try {
      const response = await createProperty(formData);
      onCreate(response); 
      onClose(); 
    } catch (error) {
      console.error("Failed to create property", error);
      // Handle error accordingly
    }
  };

  return (
    <Dialog open={open} onClose={onClose}>
      <DialogTitle>Create Property</DialogTitle>
      <DialogContent>
        <TextField name="tin" label="TIN" value={formData.tin} onChange={handleChange} fullWidth margin="normal" />
        <TextField name="address" label="Address" value={formData.address} onChange={handleChange} fullWidth margin="normal" />
        <TextField name="constructionYear" label="Construction Year" value={formData.constructionYear} onChange={handleChange} fullWidth margin="normal" />
        <TextField name="latitude" label="Latitude" value={formData.latitude} onChange={handleChange} fullWidth margin="normal" />
        <TextField name="longitude" label="Longitude" value={formData.longitude} onChange={handleChange} fullWidth margin="normal" />
        <FormControl margin="dense" fullWidth>
          <InputLabel>Property Type</InputLabel>
          <Select
            name="propertyType"
            value={formData.propertyType}
            onChange={handleChange}
          >
            <MenuItem value="DETACHED_HOUSE">Detached House</MenuItem>
            <MenuItem value="MAISONETTE">Maisonette</MenuItem>
            <MenuItem value="APARTMENT_BUILDING">Apartment Building</MenuItem>
          </Select>
        </FormControl>
        <TextField name="picture" label="Picture" value={formData.picture} onChange={handleChange} fullWidth margin="normal" />
      </DialogContent>
      <DialogActions>
        <Button onClick={onClose} color="primary">Cancel</Button>
        <Button onClick={handleSubmit} color="primary">Create</Button>
      </DialogActions>
    </Dialog>
  );
};

export default CreatePropertyDialog;
