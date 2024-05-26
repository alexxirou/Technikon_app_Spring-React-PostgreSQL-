import React, { createContext, useState, useEffect } from 'react';
import PropTypes from 'prop-types';
import {jwtDecode} from 'jwt-decode';
import useLogout from '../hooks/useLogout';
import useTokenExpiration from '../hooks/useTokenExpiration';
import useLogoutOnNavigate from '../hooks/useLogoutOnNavigate';
import { useLocation } from 'react-router-dom';

export const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
  const [authData, setAuthData] = useState(null);
  const { pathname } = useLocation();

  useEffect(() => {
    const token = localStorage.getItem('token');

    if (token) {
      try {
        const decodedToken = jwtDecode(token);
        const { tin, id, username, authorities: authoritiesArray, exp } = decodedToken;
        const authorities = authoritiesArray.map(authority => authority.authority);
        setAuthData({ userId: id, userTin: tin, username, authorities, expDate: exp });
      } catch (error) {
        console.error('Error decoding token:', error);
        setAuthData(null); // Clear auth data on error
      }
    } else {
      setAuthData(null); // Clear auth data if no token
    }
  }, [pathname]);

  const { logout, logoutDialogOpen, handleCloseLogoutDialog } = useLogout(setAuthData);
  useTokenExpiration(authData, logout);
  useLogoutOnNavigate(logout, authData);

  return (
    <AuthContext.Provider value={{ authData, setAuthData, logout, logoutDialogOpen, handleCloseLogoutDialog }}>
      {children}
    </AuthContext.Provider>
  );
};

AuthProvider.propTypes = {
  children: PropTypes.node.isRequired,
};
