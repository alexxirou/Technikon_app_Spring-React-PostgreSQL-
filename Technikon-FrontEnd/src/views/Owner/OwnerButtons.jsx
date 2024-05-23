import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import { PATHS } from '../../lib/constants';

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
      <Link to={PATHS.UPDATE_OWNER(tin)}>
        <button>Update Owner</button>
      </Link>
      <button onClick={handleClick}>
        {confirmDelete ? 'Double Click to Confirm Deletion' : 'Unsubscribe'}
      </button>
      <Link to={PATHS.PROPERTIES(id)}>
        <button>View Properties</button>
      </Link>
      <Link to={PATHS.SHOW_REPAIRS(id)}>
        <button>View Repair Jobs</button>
      </Link>
    </div>
  );
};

export default OwnerButtons;
