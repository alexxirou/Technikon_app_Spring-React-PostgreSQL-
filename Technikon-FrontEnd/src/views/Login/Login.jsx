import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import jwtDecode from 'jwt-decode';
import bcrypt from 'bcryptjs';
import { useAuth } from '../../components/useAuth';

const Login = () => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');
  const [isSubmitting, setIsSubmitting] = useState(false);
  const navigate = useNavigate();
  const { setAuthData } = useAuth(); // Use the useAuth hook to access setAuthData

  // Function to handle successful authentication
  const handleAuthentication = (token) => {
    // Decode the token to extract additional data
    const decodedToken = jwtDecode(token);
    const { authorities, claims } = decodedToken;

    // Save the necessary data in state or context
    setAuthData({ id:claims?.id, tin: claims?.tin, roles: authorities });

    // Redirect based on user role
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

    if (isSubmitting) {
      return;
    }

    try {
      setIsSubmitting(true);

      const hashedPassword = await bcrypt.hash(password, 10); 

      if (!hashedPassword) {
        throw new Error('Failed to hash password');
      }

      const response = await fetch('/api/login', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ username, password: hashedPassword, roles: ['ROLE_USER'] }), // Include roles in the request
      });

      if (!response.ok) {
        const errorMessage = response.headers.get('Error-Message') || 'Login failed';
        throw new Error(errorMessage);
      }

      const data = await response.json();

      if (data.token) {
        // Call function to handle successful authentication
        handleAuthentication(data.token);
      } else {
        setError('Login failed: Invalid response');
      }
    } catch (error) {
      setError(error.message || 'An unexpected error occurred');
      console.error('Error:', error);
    } finally {
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
