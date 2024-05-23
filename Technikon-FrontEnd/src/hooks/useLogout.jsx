import { useNavigate } from 'react-router-dom';
import axios from 'axios';

const useLogout = (setAuthData) => {
  const navigate = useNavigate();

  const logout = async () => {
    try {
      const response = await axios.post('http://localhost:5001/auth/logout', null, {
        withCredentials: true,
      });

      if (response.status === 200) {
        console.log(response.status);
        localStorage.removeItem('token');

        // Update authentication state
        setAuthData(false);

        // Navigate to the home page
        navigate('/');
      } else {
        console.error('Logout failed:', response.data);
      }
    } catch (error) {
      console.error('Error logging out:', error);
    }
  };

  return logout;
};

export default useLogout;
