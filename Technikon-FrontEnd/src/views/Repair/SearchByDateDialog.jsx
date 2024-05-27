import React, { useState } from 'react';
import { Dialog, DialogTitle, DialogContent, DialogActions, Button, TextField, Box } from '@mui/material';

const SearchByDateDialog = ({ open, onClose, onSearch }) => {
  const [date, setDate] = useState('');

  const handleSearch = () => {
    onSearch(date);
    onClose();
  };

  return (
    <Dialog open={open} onClose={onClose}>
      <DialogTitle>Search Repairs by Date</DialogTitle>
      <DialogContent>
        <Box p={2} minHeight="auto">
          <TextField
            label="Date"
            type="date"
            fullWidth
            value={date}
            onChange={(e) => setDate(e.target.value)}
            InputLabelProps={{ shrink: true }}
          />
        </Box>
      </DialogContent>
      <DialogActions>
        <Button onClick={onClose} color="secondary">Cancel</Button>
        <Button onClick={handleSearch} color="primary">Search</Button>
      </DialogActions>
    </Dialog>
  );
};

export default SearchByDateDialog;
