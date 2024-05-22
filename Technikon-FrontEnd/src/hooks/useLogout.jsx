import { useNavigate } from 'react-router-dom';


const useLogout = (setAuthData) => {
  const navigate = useNavigate();


  const handleLogout = async () => {
    try {
      await Promise.all([
        localStorage.removeItem('token'),
        setAuthData(false)
      ]);
      navigate('/');
    } catch (error) {
      console.error('Error logging out:', error);
    }
  };

  return handleLogout;
};

export default useLogout;
