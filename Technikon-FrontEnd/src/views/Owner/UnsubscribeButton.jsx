import { useState, forwardRef } from 'react';
import { Button } from '@mui/material';
import { useAuth } from '../../hooks/useAuth';


const UnsubscribeButton = forwardRef(({ handleDeleteOwner }, ref) => {
  const [confirmDelete, setConfirmDelete] = useState(false);
  const { authData, logout } = useAuth();
  
  

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
    <Button ref={ref} onClick={handleClick} variant="contained" color={confirmDelete ? 'secondary' : 'primary'}>
      {confirmDelete ? 'Double Click to Confirm Deletion' : 'Unsubscribe'}
    </Button>
  );
});

UnsubscribeButton.displayName = 'UnsubscribeButton';

export default UnsubscribeButton;
