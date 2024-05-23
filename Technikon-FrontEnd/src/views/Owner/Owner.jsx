import  { useState, useEffect } from 'react';
import api from '../../api/Api';
import { useAuth } from '../../hooks/useAuth';
import OwnerDetails from './OwnerDetails';
import OwnerButtons from './OwnerButtons';

const Owner = () => {
  const [ownerDetails, setOwnerDetails] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const { authData, handleLogout } = useAuth();
  const tin = authData.userTin;
  const propertyOwnerId = authData.userId;
  console.log(propertyOwnerId);

  useEffect(() => {
    const fetchOwnerDetails = async () => {
      try {
        const response = await api.get(`/api/propertyOwners/${tin}`);

        if (response.status === 200) {
          setOwnerDetails(response.data.userInfo);
          setLoading(false);
        } else {
          const errorMessage = response.data.message;
          throw new Error(errorMessage);
        }
      } catch (error) {
        console.error('Error:', error);

        if (error.response) {
          setError(error.response.data);
        } else if (error.request) {
          setError('No response received from the server');
        } else {
          setError('An unexpected error occurred');
        }

        setLoading(false);
      }
    };

    fetchOwnerDetails();
  }, []);

  const handleDeleteOwner = async () => {
    setLoading(true);
    try {
      const response = await api.delete(`/api/propertyOwners/${tin}`);

      if (response.status === 202) {
        console.log('Owner deleted successfully');
        const logout = handleLogout();
        logout();
      } else {
        const errorMessage = response.data.message;
        throw new Error(errorMessage);
      }
    } catch (error) {
      console.error('Error:', error);

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
  };

  

  if (loading) {
    return <div>Loading...</div>;
  }

  if (error) {
    return <div>Error: {error}</div>;
  }

  if (!ownerDetails) {
    return <div>No owner details found.</div>;
  }

  return (
    <div>
      <OwnerDetails ownerDetails={ownerDetails} />
      <OwnerButtons tin={tin} id={propertyOwnerId} handleDeleteOwner={handleDeleteOwner} />
    </div>
  );
};

export default Owner;
