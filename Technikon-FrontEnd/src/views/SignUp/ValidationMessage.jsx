// src/components/ValidationMessage.js

import { Typography } from '@mui/material';

const ValidationMessage = ({ message }) => {
  return (
    <Typography variant="caption" color="error">
      {message}
    </Typography>
  );
};

export default ValidationMessage;
