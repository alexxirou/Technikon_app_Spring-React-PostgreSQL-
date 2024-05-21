import api from '../api/Api';
import { useAuth } from '../hooks/useAuth';

const Owner = () => {
  const { authData } = useAuth();

  console.log(authData);

  return (
    <div>
      <h1>Owner</h1>
      <p>User ID: {authData.userId}</p>
      <p>User TIN: {authData.userTin}</p>
      <p>Username: {authData.username}</p>
      <p>Authorities: {authData.authorities?.join(', ')}</p>
    </div>
  );
};

export default Owner;
