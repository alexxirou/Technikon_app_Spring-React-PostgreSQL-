import React from 'react';
import { Dialog, DialogTitle, DialogContent, DialogContentText, DialogActions, TextField, Button } from '@mui/material';

const UpdateRepairDialog = ({ open, onClose, repair, onChange, onSubmit }) => {
  return (
    <Dialog open={open} onClose={onClose}>
      <DialogTitle>Update Repair</DialogTitle>
      <DialogContent>
        <DialogContentText>Update the repair details below:</DialogContentText>
        <TextField
          label="Short Description"
          value={repair?.shortDescription || ''}
          onChange={(e) => onChange({ ...repair, shortDescription: e.target.value })}
          fullWidth
          margin="normal"
        />
        <TextField
          label="Date of Repair"
          type="date"
          value={repair?.dateOfRepair || ''}
          onChange={(e) => onChange({ ...repair, dateOfRepair: e.target.value })}
          fullWidth
          margin="normal"
          InputLabelProps={{ shrink: true }}
        />
        <TextField
          label="Repair Type"
          value={repair?.repairType || ''}
          onChange={(e) => onChange({ ...repair, repairType: e.target.value })}
          fullWidth
          margin="normal"
        />
        <TextField
          label="Repair Status"
          value={repair?.repairStatus || ''}
          onChange={(e) => onChange({ ...repair, repairStatus: e.target.value })}
          fullWidth
          margin="normal"
        />
        <TextField
          label="Cost"
          value={repair?.cost || ''}
          onChange={(e) => onChange({ ...repair, cost: e.target.value })}
          fullWidth
          margin="normal"
        />
        <TextField
          label="Long Description"
          value={repair?.longDescription || ''}
          onChange={(e) => onChange({ ...repair, longDescription: e.target.value })}
          fullWidth
          margin="normal"
        />
      </DialogContent>
      <DialogActions>
        <Button onClick={onClose} color="primary">Cancel</Button>
        <Button onClick={onSubmit} color="primary">Update</Button>
      </DialogActions>
    </Dialog>
  );
};

export default UpdateRepairDialog;
