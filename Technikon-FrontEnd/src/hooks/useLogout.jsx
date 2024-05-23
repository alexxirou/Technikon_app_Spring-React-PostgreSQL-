import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import LogoutDialog from '../components/LogoutDialog';

const useLogout = (setAuthData) => {
  const [logoutDialogOpen, setLogoutDialogOpen] = useState(false);
  const navigate = useNavigate();

  const logout = async () => {
    try {
      const response = await axios.post('http://localhost:5001/auth/logout', null, {
        withCredentials: true,
      });

      if (response.status === 200) {
        localStorage.removeItem('token');

        // Update authentication state
        setAuthData(false);

        // Show the logout dialog
        setLogoutDialogOpen(true);
      } else {
        console.error('Logout failed:', response.data);
      }
    } catch (error) {
      console.error('Error logging out:', error);
    }
  };

  const handleCloseLogoutDialog = () => {
    setLogoutDialogOpen(false);
    navigate('/');
  };

  return {
    logout,
    logoutDialogOpen,
    handleCloseLogoutDialog,
  };
};

export default useLogout;
