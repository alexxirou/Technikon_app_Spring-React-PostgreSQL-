import  { useState, useEffect } from 'react';
import api  from '../api/Api';
import { useAuth } from '../hooks/useAuth';

const Owner = () => {
  const [ownerDetails, setOwnerDetails] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const { authData } = useAuth();
  const tin = authData.userTin;
  

  useEffect(() => {
    const fetchOwnerDetails = async () => {
      try {
        // Make an Axios request to fetch owner details
        const response = await api.get(`/api/propertyOwners/${tin}`); // Adjust the URL as per your backend API endpoint
        
        if (response.status === 200) {
          setOwnerDetails(response.data.userInfo);
          console.log(response.data.userInfo);
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
      <h1>Owner Details</h1>
      <p>User TIN: {ownerDetails.tin}</p>
      <p>Username: {ownerDetails.username}</p>
    
      
      <p>User Email: {ownerDetails.email}</p>
      <p>User First Name: {ownerDetails.name}</p>
      <p>User Last Name: {ownerDetails.surname}</p>
      <p>User Address: {ownerDetails.address}</p>
      <p>User Phone number: {ownerDetails.phoneNumber}</p>
    </div>
  )
  
};

export default Owner;
