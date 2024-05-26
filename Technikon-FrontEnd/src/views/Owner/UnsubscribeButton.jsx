import  { useState } from 'react';
import { Button } from '@mui/material';

const UnsubscribeButton = ({ handleDeleteOwner }) => {
  const [confirmDelete, setConfirmDelete] = useState(false);

  const handleClick = () => {
    if (confirmDelete) {
      handleDeleteOwner();
    } else {
      setConfirmDelete(true);
      setTimeout(() => {
        setConfirmDelete(false);
      }, 2000);
    }
  };

  return (
    <Button onClick={handleClick} variant="contained" color={confirmDelete ? 'secondary' : 'primary'}>
      {confirmDelete ? 'Double Click to Confirm Deletion' : 'Unsubscribe'}
    </Button>
  );
};

export default UnsubscribeButton;
