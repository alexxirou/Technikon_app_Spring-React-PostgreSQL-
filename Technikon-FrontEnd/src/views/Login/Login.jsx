import  { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { jwtDecode } from "jwt-decode";
import { useAuth } from '../../hooks/useAuth';
import axios from 'axios';



const Login = () => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');
  const navigate = useNavigate();
  const { setAuthData} = useAuth();

  // Load isSubmitting state from localStorage or default to false
  const [isSubmitting, setIsSubmitting] = useState(() => {
    return localStorage.getItem('isSubmitting')? localStorage.getItem('isSubmitting') === 'true' : false;
  });


  useEffect(() => {
    // Load isSubmitting state from localStorage or default to false
    const storedValue = localStorage.getItem('isSubmitting');
    setIsSubmitting(storedValue ? storedValue === 'true' : false);
    
    // Set submitting state to false in localStorage when component unmounts
    return () => {
      localStorage.setItem('isSubmitting', 'false');
    };
  }, []);
  
  // Use useEffect to execute logic dependent on authData



  const  handleAuthentication = async (tin, id, username, authorities, exp) => {
    await setAuthData({ userId:id, userTin:tin, username:username, authorities: authorities, expDate:exp });


    if (authorities.includes('ROLE_ADMIN')) {
      navigate(`/api/admin}`);
    } else if (authorities.includes('ROLE_USER')) {
      navigate(`/api/owner`);
    } else {
      navigate('/');
    }
  };

  

  const handleSubmit = async (event) => {
    event.preventDefault();
  
    // Prevent multiple submissions
    if (isSubmitting) {
      return;
    }
  
    try {
      setIsSubmitting(true);
  
      const response = await axios.post('http://localhost:5001/auth/login', {
        username,
        password,
      }, {
        withCredentials: true,
      });
  
      if (response.status === 200) {
        const data = await response.data;
        if (data.token) {
          localStorage.setItem('token', data.token);
          const decodedToken = jwtDecode(data.token);
        
          const { tin, id, username, authorities: authoritiesArray, exp } = decodedToken;
          const authorities = authoritiesArray.map(authority => authority.authority);

          handleAuthentication(tin, id, username, authorities, exp);
        } else {
          setError('Login failed: Invalid response');
        }
      } else {
        // Handle other status codes
        const errorMessage = response.data.message || 'Login failed';
        throw new Error(errorMessage);
      }
    } catch (error) {
      // Handle errors
      setError(error.message || 'An unexpected error occurred');
      console.error('Error:', error);
    } finally {
      // Set submitting state to false
      setIsSubmitting(false);
     
    }
  };
  

  return (
    <div className="login-container" style={{ marginTop: '0' }}>
      <h2>Login</h2>
      {error && <p className="error-message">{error}</p>}
      <form onSubmit={handleSubmit}>
        <div className="form-group">
          <label htmlFor="username">Username:</label>
          <input
            type="text"
            id="username"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
            required
          />
        </div>
        <div className="form-group">
          <label htmlFor="password">Password:</label>
          <input
            type="password"
            id="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            required
          />
        </div>
        <button type="submit" disabled={isSubmitting}>Login</button>
      </form>
    </div>
  );
};

export default Login;
