import { useEffect } from 'react';
import { useNavigate, useLocation } from 'react-router-dom';
import api from '../api/Api'

const useTokenExpiration = (authData, logout) => {
  const navigate = useNavigate();
  const location = useLocation();

  useEffect(() => {
    const checkTokenExpiration = async () => {
      try {
        // Make API call to get server time
        const response = await api.get('/api/time'); // Adjust the endpoint URL as per your server setup
        const { timestamp } = await response.json();
        console.log(timestamp)
        // Compare server time with token expiration time
        const isTokenExpired = authData && authData.expDate < timestamp;

        // If token is expired and user is not on login or sign up page, logout
        if (isTokenExpired && (location.pathname !== '/login' || location.pathname !== '/signUp')) {
          logout();
        }
      } catch (error) {
        console.error('Error fetching server time:', error);
      }
    };

    checkTokenExpiration();
  }, [authData, logout, navigate, location.pathname]);
};

export default useTokenExpiration;
