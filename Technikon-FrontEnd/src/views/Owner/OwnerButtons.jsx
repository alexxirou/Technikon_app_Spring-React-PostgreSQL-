import React, { useState } from 'react';
import { Link } from 'react-router-dom';

const OwnerButtons = ({ tin, id, handleDeleteOwner }) => {
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
    <div>
      <Link to={`/update-owner/${tin}`}>
        <button>Update Owner</button>
      </Link>
      <button onClick={handleClick}>
        {confirmDelete ? 'Double Click to Confirm Deletion' : 'Unsubscribe'}
      </button>
      <Link to={`/properties/${tin}`}>
        <button>View Properties</button>
      </Link>
      <Link to={`/repair/${id}`}>
        <button>View Repair Jobs</button>
      </Link>
    </div>
  );
};

export default OwnerButtons;
