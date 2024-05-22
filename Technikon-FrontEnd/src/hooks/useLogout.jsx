import { useNavigate } from 'react-router-dom';

const useLogout = (setIsLoggedIn) => {
  const navigate = useNavigate();

  const handleLogout = () => {
    localStorage.removeItem('authData');
    localStorage.removeItem('token');
    setIsLoggedIn(false);
    navigate('/');
  };

  return handleLogout;
};

export default useLogout;
