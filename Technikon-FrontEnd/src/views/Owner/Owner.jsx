import { useState, useEffect } from 'react';
import api from '../../api/Api';
import { useAuth } from '../../hooks/useAuth';
import OwnerDetails from './OwnerDetails';
import CircularProgress from '@mui/material/CircularProgress';
import { useNavigate } from 'react-router-dom';
import { PATHS } from '../../lib/constants';

const Owner = () => {
  const [ownerDetails, setOwnerDetails] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const { authData, logout } = useAuth();
  const tin = authData?.userTin;
  const navigate = useNavigate();

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
      setLoading(true);
     
      try {
        const response = await api.delete(`/api/propertyOwners/${tin}`);
     
  
      
        if (response.status === 202) {
          console.log('Owner deleted successfully');
          
          setTimeout(() => {
            logout();
          }, 2000);
     
        } else {
          const errorMessage = response.data.message;
          throw new Error(errorMessage);
        }
      } catch (error) {
      
        console.error('Error:', error);
        setError(error.message);
  
        if (error.response) {
          setError(error.response.data);
        } else if (error.request) {
          setError('No response received from the server');
        } else {
          setError('An unexpected error occurred');
        }
      } finally {
        setLoading(false);
      }
      }
  

  
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
