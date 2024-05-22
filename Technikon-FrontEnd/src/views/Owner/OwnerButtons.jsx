import React, { useState } from 'react';
import { Link } from 'react-router-dom';

const OwnerButtons = ({ tin, handleDeleteOwner }) => {
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
      <Link to={`/api/update-owner/${tin}`}>
        <button>Update Owner</button>
      </Link>
      <button onClick={handleClick}>
        {confirmDelete ? 'Double Click to Confirm Deletion' : 'Unsubscribe'}
      </button>
      <Link to={`/api/properties/${tin}`}>
        <button>View Properties</button>
      </Link>
      <Link to={`/api/repair/${tin}`}>
        <button>View Repair Jobs</button>
      </Link>
    </div>
  );
};

export default OwnerButtons;
