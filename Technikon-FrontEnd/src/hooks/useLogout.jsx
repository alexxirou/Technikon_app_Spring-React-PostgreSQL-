import { useNavigate } from 'react-router-dom';

const useLogout = () => {
  const navigate = useNavigate();

  const handleLogout = () => {
    localStorage.removeItem('authData');
    localStorage.removeItem('token');
    
    navigate('/');
  };

  return handleLogout;
};

export default useLogout;
