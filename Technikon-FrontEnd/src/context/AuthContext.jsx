import  { createContext, useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import PropTypes from 'prop-types';

export const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
  const [authData, setAuthData] = useState(null);
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [authorities, setAuthorities] = useState([]);
  const navigate = useNavigate();


  useEffect(() => {
    const storedAuthData = localStorage.getItem('authData');
    if (storedAuthData) {
      const parsedAuthData = storedAuthData;
      setAuthData(parsedAuthData);
      const isTokenExpired = parsedAuthData.expDate < Date.now() / 1000;
      if (isTokenExpired) {
        clearToken();
      } else {
        setIsLoggedIn(true);
        setAuthorities(parsedAuthData.authorities || []);
      }
    }
  }, []);

  const clearToken = () => {
    localStorage.removeItem('authData');
    localStorage.removeItem('token');
    setIsLoggedIn(false);
  
  };
  useEffect(() => {
    if (location.pathname === '/login' || location.pathname === '/signup') {
      clearToken ();
      navigate(location.pathname);
    }
  }, [location.pathname]);

  const handleLogout = () => {
    clearToken();
    navigate('/');
  };

  return (
    <AuthContext.Provider value={{ authData, setAuthData, isLoggedIn, authorities, handleLogout }}>
      {children}
    </AuthContext.Provider>
  );
};

AuthProvider.propTypes = {
  children: PropTypes.node.isRequired,
};