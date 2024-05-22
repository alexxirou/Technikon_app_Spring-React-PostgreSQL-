import { useEffect } from 'react';
import { useNavigate, useLocation } from 'react-router-dom';

const useTokenExpiration = (authData, setAuthData) => {
  const navigate = useNavigate();
  const location = useLocation();

  useEffect(() => {
    const isTokenExpired = authData && authData.expDate < Date.now() / 1000;

    if (isTokenExpired && (location.pathname !== '/login' || location.pathname !== '/signUp')) {

      localStorage.removeItem('token');
      setAuthData(null);

  
      navigate('/');
    }
  }, [authData, setAuthData, navigate, location.pathname]);

  
};

export default useTokenExpiration;