import { useState, useEffect } from 'react';
import api from '../../api/Api';
import { useAuth } from '../../hooks/useAuth';
import OwnerDetails from './OwnerDetails';
import CircularProgress from '@mui/material/CircularProgress';

const Owner = () => {
  const [ownerDetails, setOwnerDetails] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const { authData, logout } = useAuth();
  const tin = authData?.userTin;

  useEffect(() => {
    const fetchOwnerDetails = async () => {
      try {
        const response = await api.get(`/api/propertyOwners/${tin}`);
        if (response.status === 200) {
          setOwnerDetails(response.data.userInfo);
        } else {
          throw new Error('Failed to fetch owner details');
        }
      } catch (error) {
        setError(error.message);
      } finally {
        setLoading(false);
      }
    };

    fetchOwnerDetails();
  }, [tin]);

  const handleDeleteOwner = async () => {
    try {
      await api.delete(`/api/propertyOwners/${tin}`);
      logout(); // Logout after successful deletion
    } catch (error) {
      setError(error.message);
    }
  };

  
  if (loading) {
    return <CircularProgress />;
  }

  if (error) {
    return <div>Error: {error}</div>;
  }

  if (!ownerDetails) {
    return <div>No owner details found.</div>;
  }

  return (
    <OwnerDetails
      ownerDetails={ownerDetails}
      handleDeleteOwner={handleDeleteOwner}
      setOwnerDetails={setOwnerDetails}
    />
  );
  
};

export default Owner;
