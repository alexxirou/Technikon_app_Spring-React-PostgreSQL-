import { useEffect } from 'react';
import {  useLocation, useNavigate } from 'react-router-dom';
import { PATHS } from '../lib/constants';
import api from '../api/Api';


const useTokenExpiration = (authData, logout) => {
  const location = useLocation();
  const navigate= useNavigate();

  useEffect(() => {
    const checkTokenExpiration = async () => {
      try {
        // Make API call to get server time
        const response = await api.get('/api/time');
      

        // Ensure the response contains the expected timestamp
        const serverTimestamp = response.data;
        if (typeof serverTimestamp !== 'number') {
          console.error('Invalid server timestamp:', serverTimestamp);
          return;
        }
        console.log('Server timestamp:', serverTimestamp);

        if (!authData) {
          console.log('No auth data available');
          return;
        }

        // Check if authData and expDate are defined
        if (!authData.expDate) {
          console.error('Token expiration date is missing in authData');
          return;
        }

        // Convert expiration date from seconds to milliseconds for comparison
        const expirationDateInMilliseconds = authData.expDate * 1000;
       

        // Compare server time with token expiration time
        const isTokenExpired = expirationDateInMilliseconds < serverTimestamp;

        // If token is expired and user is not on login or sign-up page, logout
        if (isTokenExpired) {
          console.log('Token is expired, logging out');
          navigate(PATHS.LOGIN);
        } else {
          console.log('Token is still valid');
        }
      } catch (error) {
        console.error('Error fetching server time:', error);
      }
    };

    if (authData) {
      checkTokenExpiration();
    }
  }, [authData, logout, location.pathname]);

  return null; // This hook does not need to render anything
};

export default useTokenExpiration;
