import { createContext, useState, useEffect } from 'react';

import PropTypes from 'prop-types';
import { jwtDecode } from "jwt-decode";

export const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
  const [authData,setAuthData] = useState(null);
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [authorities, setAuthorities] = useState([]);


  useEffect(() => {
    // Retrieve token from local storage
    const token = localStorage.getItem('token');
  
    // If token is found in local storage, decode it and set the authentication data
    if (token) {
      try {
        // Decode the token (assuming it's a JWT)
        const decodedToken = jwtDecode(token);
        
          const { tin, id, username, authorities: authoritiesArray, exp } = decodedToken;
          const authorities = authoritiesArray.map(authority => authority.authority);
          setAuthData({ userId:id, userTin:tin, username:username, authorities: authorities, expDate:exp });
       
       
      } catch (error) {
        console.error('Error decoding token:', error);
      }
    }
  }, []);

  useEffect(() => {
    const checkTokenExpiration = () => {
      if (authData ) {
        const isTokenExpired = authData.expDate < Date.now() / 1000;
        if (isTokenExpired) {
          handleLogout(); // Token expired, logout user
       
        }
      }
    };

    checkTokenExpiration();

  }, [authData]);


  const handleLogout = () => {
    localStorage.removeItem('authData');
    setIsLoggedIn(false);
    setAuthData(null);
    setAuthorities([]);
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
