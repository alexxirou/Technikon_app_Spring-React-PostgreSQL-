import  { useState, useEffect} from 'react';
import { useNavigate } from 'react-router-dom';
import jwtDecode from 'jwt-decode';
import { useAuth } from './useAuth';
import axios from 'axios';

const Login = () => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');
  const navigate = useNavigate();
  const { setAuthData } = useAuth();

  // Load isSubmitting state from localStorage or default to false
  const [isSubmitting, setIsSubmitting] = useState(() => {
    return localStorage.getItem('isSubmitting') === 'true';
  });

  useEffect(() => {
    // Update localStorage whenever isSubmitting changes
    localStorage.setItem('isSubmitting', isSubmitting);
  }, [isSubmitting]);

  const handleAuthentication = (token) => {
    const decodedToken = jwtDecode(token);
    const { authorities, claims } = decodedToken;

    setAuthData({ id: claims?.id, tin: claims?.tin, roles: authorities });

    if (authorities.includes('ROLE_ADMIN')) {
      navigate(`/api/v2/admin/${claims?.tin}`);
    } else if (authorities.includes('ROLE_USER')) {
      navigate(`/api/v2/propertyowner/${claims?.tin}`);
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
      // Set submitting state to true
      setIsSubmitting(true);
  
      // Send login request
      const response = await axios.post('http://localhost:5001/login', {
        username,
        password,
      });
  
      // Check response status
      if (response.status === 200) {
        // Login successful
        const data = response.data;
        if (data.token) {
          handleAuthentication(data.token);
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
    <div className="login-container">
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
