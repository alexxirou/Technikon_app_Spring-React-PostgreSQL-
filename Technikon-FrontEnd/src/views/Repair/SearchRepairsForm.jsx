import React, { useState } from 'react';
import { Box, Button, TextField } from '@mui/material';

const SearchRepairsForm = ({ onSearchByDate, onSearchByDateRange }) => {
  const [date, setDate] = useState('');
  const [startDate, setStartDate] = useState('');
  const [endDate, setEndDate] = useState('');

  const handleDateChange = (event) => {
    setDate(event.target.value);
  };

  const handleStartDateChange = (event) => {
    setStartDate(event.target.value);
  };

  const handleEndDateChange = (event) => {
    setEndDate(event.target.value);
  };

  const handleSearchByDate = () => {
    onSearchByDate(date);
  };

  const handleSearchByDateRange = () => {
    onSearchByDateRange(startDate, endDate);
  };

  return (
    <Box mb={2}>
      <Box display="flex" justifyContent="space-between" mb={2}>
        <TextField
          label="Date"
          type="date"
          value={date}
          onChange={handleDateChange}
          InputLabelProps={{ shrink: true }}
        />
        <Button variant="contained" color="primary" onClick={handleSearchByDate}>
          Search by Date
        </Button>
      </Box>
      <Box display="flex" justifyContent="space-between" mb={2}>
        <TextField
          label="Start Date"
          type="date"
          value={startDate}
          onChange={handleStartDateChange}
          InputLabelProps={{ shrink: true }}
        />
        <TextField
          label="End Date"
          type="date"
          value={endDate}
          onChange={handleEndDateChange}
          InputLabelProps={{ shrink: true }}
        />
        <Button variant="contained" color="primary" onClick={handleSearchByDateRange}>
          Search by Date Range
        </Button>
      </Box>
    </Box>
  );
};

export default SearchRepairsForm;
