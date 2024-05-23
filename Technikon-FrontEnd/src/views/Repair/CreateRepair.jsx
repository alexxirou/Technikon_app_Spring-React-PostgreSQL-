import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { Container, TextField, Button, MenuItem, Box, Typography, Dialog, DialogActions, DialogContent, DialogContentText, DialogTitle } from '@mui/material';
import api from '../../api/Api';
import { useAuth } from '../../hooks/useAuth';
import { PATHS } from '../../lib/constants';

const CreateRepair = () => {
  const navigate = useNavigate();
  const [dateOfRepair, setDateOfRepair] = useState('');
  const [shortDescription, setShortDescription] = useState('');
  const [repairType, setRepairType] = useState('');
  const [repairStatus, setRepairStatus] = useState('');
  const [cost, setCost] = useState('');
  const [longDescription, setLongDescription] = useState('');
  const [errors, setErrors] = useState({});
  const { authData } = useAuth();
  const [success, setSuccess] = useState(false);

  const token = localStorage.getItem('token');
  // console.log(token);

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

  const handleSubmit = async (event) => {
    event.preventDefault();

    const isCostValid = validateCost(cost);
    const isDateValid = validateDate(dateOfRepair);

    if (!isCostValid || !isDateValid) {
      return;
    }

    const propertyOwnerId = authData.userId;
    const propertyId = 1;
    const repairData = {
      id: 0,
      propertyOwnerId,
      propertyId,
      dateOfRepair,
      shortDescription,
      repairType,
      repairStatus,
      cost,
      longDescription,
    };

    try {
      const response = await api.post('/api/property-repairs/create', repairData);
      console.log(response.data);
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

  return (
    <Container maxWidth="sm">
      <Box
        display="flex"
        flexDirection="column"
        alignItems="center"
        justifyContent="center"
        minHeight="100vh"
      >
        <form onSubmit={handleSubmit}>
          <TextField
            label="Date of Repair"
            type="date"
            value={dateOfRepair}
            onChange={(e) => {
              setDateOfRepair(e.target.value);
              validateDate(e.target.value);
            }}
            fullWidth
            required
            InputLabelProps={{
              shrink: true,
            }}
            margin="normal"
            error={!!errors.dateOfRepair}
            helperText={errors.dateOfRepair}
          />
          <TextField
            label="Short Description"
            value={shortDescription}
            onChange={(e) => setShortDescription(e.target.value)}
            fullWidth
            required
            margin="normal"
          />
          <TextField
            label="Repair Type"
            value={repairType}
            onChange={(e) => setRepairType(e.target.value)}
            fullWidth
            select
            required
            margin="normal"
          >
            <MenuItem value="PLUMBING">PLUMBING</MenuItem>
            <MenuItem value="ELECTRICAL_WORK">ELECTRICAL WORK</MenuItem>
            <MenuItem value="INSULATION">INSULATION</MenuItem>
            <MenuItem value="PAINTING">PAINTING</MenuItem>
            <MenuItem value="FRAMES">FRAMES</MenuItem>
          </TextField>
          <TextField
            label="Repair Status"
            value={repairStatus}
            onChange={(e) => setRepairStatus(e.target.value)}
            fullWidth
            select
            required
            margin="normal"
          >
            <MenuItem value="DEFAULT_PENDING">PENDING</MenuItem>
            <MenuItem value="SCHEDULED">SCHEDULED</MenuItem>
            <MenuItem value="IN_PROGRESS">IN PROGRESS</MenuItem>
            <MenuItem value="COMPLETE">COMPLETED</MenuItem>
          </TextField>
          <TextField
            label="Cost"
            type="number"
            value={cost}
            onChange={(e) => {
              const newCost = e.target.value;
              if (newCost >= 0) {
                setCost(newCost);
                validateCost(newCost);
              }
            }}
            error={!!errors.cost}
            helperText={errors.cost}
            fullWidth
            required
            margin="normal"
            inputProps={{ min: 0 }}
          />
          <TextField
            label="Long Description"
            value={longDescription}
            onChange={(e) => setLongDescription(e.target.value)}
            fullWidth
            multiline
            rows={4}
            margin="normal"
          />
          {errors.submit && (
            <Typography color="error" variant="body2">
              {errors.submit}
            </Typography>
          )}
          <Button
            type="submit"
            variant="contained"
            color="primary"
            fullWidth
            style={{ marginTop: '20px' }}
          >
            Create Repair
          </Button>
        </form>
        <Dialog
          open={success}
          onClose={() => setSuccess(false)}
        >
          <DialogTitle>Repair created!</DialogTitle>
          <DialogContent>
            <DialogContentText>
              The property repair has been created successfully. You can now go to the repairs page.
            </DialogContentText>
          </DialogContent>
          <DialogActions>
            <Button onClick={() => navigate(PATHS.SHOW_REPAIRS)} color="primary">
              Go to Repairs
            </Button>
          </DialogActions>
        </Dialog>
      </Box>
    </Container>
  );
};

export default CreateRepair;
