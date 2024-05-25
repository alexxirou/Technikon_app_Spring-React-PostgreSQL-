import { useEffect } from 'react';
import { useLocation } from 'react-router-dom';

const useLogoutOnNavigate = (logout, authData) => {
  const location = useLocation();

  useEffect(() => {
    // Check if the user navigates to the login or signup page
    if (location.pathname === '/login' || location.pathname === '/signup') {
      if (authData) logout(); 
      
    }
  }, [location, logout]);

  return null; // This hook does not need to render anything
};

export default useLogoutOnNavigate;
