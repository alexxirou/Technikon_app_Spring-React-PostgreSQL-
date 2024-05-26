
import { Link } from 'react-router-dom';
import { PATHS } from '../../lib/constants';
import { Button } from '@mui/material';
import UpdateOwner from './UpdateOwner';

const OwnerButtonsComponent = ({ tin, id, updateModalOpen, handleCloseUpdateModal, ownerDetails, setOwnerDetails }) => {
  const ownerState = {
    color: 'primary', // Set a default color value or fetch it from ownerDetails or other source
    
  };


  return (
    <div style={{ display: 'flex', flexDirection: 'column', gap: '8px' }}>
      <UpdateOwner open={updateModalOpen} handleClose={handleCloseUpdateModal} ownerDetails={ownerDetails} setOwnerDetails={setOwnerDetails}  />
      <Button component={Link} to={PATHS.PROPERTIES(id)} variant="contained" color={ownerState.color}>
        View Properties
      </Button>
      <Button component={Link} to={PATHS.SHOW_REPAIRS(id)} variant="contained" color={ownerState.color}>
        View Repair Jobs
      </Button>
    </div>
  );
};
OwnerButtonsComponent.displayName = 'OwnerButtonsComponent';

export default OwnerButtonsComponent;
