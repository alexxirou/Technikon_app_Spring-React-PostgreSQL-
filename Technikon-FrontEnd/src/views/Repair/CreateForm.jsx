import { TextField, MenuItem, Button, Typography } from '@mui/material';

const CreateForm = ({
  formData,
  setFormData,
  errors,
  handleSubmit
}) => {
  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({
      ...formData,
      [name]: value
    });
  };

  return (
    <form onSubmit={handleSubmit}>
      <TextField
        name="dateOfRepair"
        label="Date of Repair"
        type="date"
        value={formData.dateOfRepair}
        onChange={handleChange}
        fullWidth
        required
        InputLabelProps={{ shrink: true }}
        margin="normal"
        error={!!errors.dateOfRepair}
        helperText={errors.dateOfRepair}
      />
      <TextField
        name="shortDescription"
        label="Short Description"
        value={formData.shortDescription}
        onChange={handleChange}
        fullWidth
        required
        margin="normal"
      />
      <TextField
        name="repairType"
        label="Repair Type"
        value={formData.repairType}
        onChange={handleChange}
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
        name="repairStatus"
        label="Repair Status"
        value={formData.repairStatus}
        onChange={handleChange}
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
        name="cost"
        label="Cost"
        type="number"
        value={formData.cost}
        onChange={handleChange}
        error={!!errors.cost}
        helperText={errors.cost}
        fullWidth
        required
        margin="normal"
        inputProps={{ min: 0 }}
      />
      <TextField
        name="longDescription"
        label="Long Description"
        value={formData.longDescription}
        onChange={handleChange}
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
  );
};

export default CreateForm;
