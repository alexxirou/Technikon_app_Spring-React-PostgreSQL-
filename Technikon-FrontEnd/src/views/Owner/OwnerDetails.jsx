import  { useState } from 'react';
import { Typography, Paper, Container, Grid } from '@mui/material';
import OwnerButtonsComponent from './OwnerButtons'; // Import the OwnerButtonsComponent
import UnsubscribeButton from './UnsubscribeButton'; // Import the UnsubscribeButtonComponent


const OwnerDetails = ({ ownerDetails, handleDeleteOwner, setOwnerDetails }) => {
  // State for controlling the visibility of the update modal
  const [updateModalOpen, setUpdateModalOpen] = useState(false);

  // Function to open the update modal
  const handleOpenUpdateModal = () => {
    setUpdateModalOpen(true);
  };

  // Function to close the update modal
  const handleCloseUpdateModal = () => {
    setUpdateModalOpen(false);
    
  };

  return (
    <Container style={{ maxWidth: '900px' }}>
      <Paper sx={{ bgcolor: 'primary.main', color: 'white', textAlign: 'center', p: 4, mb: 2 }}>
        <Typography variant="h4" gutterBottom>
          Owner Details
        </Typography>
      </Paper>
      <Paper sx={{ p: 2 }}>
        <Grid container spacing={2} justifyContent="center">
          {/* Grid item for text */}
          <Grid item xs={12} md={4}>
            <Typography variant="body1">User TIN: {ownerDetails.tin}</Typography>
            <Typography variant="body1">Username: {ownerDetails.username}</Typography>
            <Typography variant="body1">User Email: {ownerDetails.email}</Typography>
            <Typography variant="body1">User First Name: {ownerDetails.name}</Typography>
            <Typography variant="body1">User Last Name: {ownerDetails.surname}</Typography>
            <Typography variant="body1">User Address: {ownerDetails.address}</Typography>
            <Typography variant="body1">User Phone number: {ownerDetails.phoneNumber}</Typography>
          </Grid>
          {/* Grid item for buttons */}
          <Grid item xs={12} md={4} container justifyContent="center" mt={2}>
            {/* Render OwnerButtonsComponent */}
            <OwnerButtonsComponent tin={ownerDetails.tin} id={ownerDetails.id} handleDeleteOwner={handleDeleteOwner} ownerDetails={ownerDetails} setOwnerDetails={setOwnerDetails}  />
          </Grid>
        </Grid>
      </Paper>
      {/* Unsubscribe button */}
      <Container maxWidth="md" sx={{ mt: 2, textAlign: 'center' }}>
        <UnsubscribeButton handleDeleteOwner={handleDeleteOwner} />
      </Container>

      
    </Container>
  );
};

export default OwnerDetails;
