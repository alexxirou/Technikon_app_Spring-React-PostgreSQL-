import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { Container, TextField, Button, MenuItem, Box } from '@mui/material';
import axios from 'axios';

const CreateRepair = () => {
  const navigate = useNavigate();
  const [dateOfRepair, setDateOfRepair] = useState('');
  const [shortDescription, setShortDescription] = useState('');
  const [repairType, setRepairType] = useState('');
  const [repairStatus, setRepairStatus] = useState('');
  const [cost, setCost] = useState('');
  const [longDescription, setLongDescription] = useState('');
  const [errors, setErrors] = useState({});

  const validateCost = (cost) => {
    if (cost <= 0) {
      setErrors((prevErrors) => ({
        ...prevErrors,
        cost: 'Insert a valid cost'
      }));
    } else {
      setErrors((prevErrors) => {
        const { cost, ...rest } = prevErrors;
        return rest;
      });
    }
  };

  const handleSubmit = async (event) => {
    event.preventDefault();

    if (!validateCost(cost)) {
      return;
    }

    const repairData = {
      dateOfRepair,
      shortDescription,
      repairType,
      repairStatus,
      cost,
      longDescription,
    };

    try {
      const response = await axios.post('http://localhost:5001/api/property-repairs', repairData);
      console.log(response.data);
      navigate('/repairs'); // Navigate back to the repairs page after creation
    } catch (error) {
      console.error('Error creating repair:', error);
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
            onChange={(e) => setDateOfRepair(e.target.value)}
            fullWidth
            required
            InputLabelProps={{
              shrink: true,
            }}
            margin="normal"
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
      </Box>
    </Container>
  );
};

export default CreateRepair;