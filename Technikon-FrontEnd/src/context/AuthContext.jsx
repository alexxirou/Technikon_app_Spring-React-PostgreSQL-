import { createContext, useState, useEffect } from 'react';

import PropTypes from 'prop-types';
import { jwtDecode } from "jwt-decode";
import useLogout from '../hooks/useLogout';
import useTokenExpiration from '../hooks/useTokenExpiration';

export const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
  const [authData,setAuthData] = useState(null);

 


  useEffect(() => {
    // Retrieve token from local storage
    const token = localStorage.getItem('token');
  
    // If token is found in local storage, decode it and set the authentication data
    if (token) {
      console.log("1");
      console.log(token);
      try {
        // Decode the token (assuming it's a JWT)
        const decodedToken = jwtDecode(token);
      console.log("2");
        console.log(decodedToken);
        
          const { tin, id, username, authorities: authoritiesArray, exp } = decodedToken;
          const authorities = authoritiesArray.map(authority => authority.authority);
          setAuthData({ userId:id, userTin:tin, username:username, authorities: authorities, expDate:exp });
          
       
      } catch (error) {
        console.error('Error decoding token:', error);
      }
    }
  }, []);

  const handleLogout = useLogout(setAuthData)
  useTokenExpiration(authData, setAuthData);

  return (
    <AuthContext.Provider value={{ authData, setAuthData,  handleLogout }}>
      {children}
    </AuthContext.Provider>
  );
};

AuthProvider.propTypes = {
  children: PropTypes.node.isRequired,
};
