import React from 'react';
import { Container, Typography } from '@mui/material';

const Footer = () => {
  const year = new Date().getFullYear(); // Get the current year

  return (
    <Container maxWidth="sm">
      <Typography variant="body2" color="text.secondary" align="center" gutterBottom>
        © {year} Technikon. All rights reserved.
      </Typography>
    </Container>
  );
};

export default Footer;
