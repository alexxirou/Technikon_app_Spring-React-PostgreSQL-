import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import jwtDecode from 'jwt-decode';
import bcrypt from 'bcryptjs';
import './Login.css';

const Login = () => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');
  const [isSubmitting, setIsSubmitting] = useState(false);
  const navigate = useNavigate();

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
        body: JSON.stringify({ username, password: hashedPassword }),
      });

      if (!response.ok) {
        const errorMessage = response.headers.get('Error-Message') || 'Login failed';
        throw new Error(errorMessage);
      }

      const data = await response.json();

      if (data.token) {
        localStorage.setItem('token', data.token);
        const decodedToken = jwtDecode(data.token);
        const roles = decodedToken.authorities || [];
        const tin = decodedToken.claims?.tin;

        if (roles.includes('ROLE_ADMIN')) {
          navigate(`/api/v2/admin/${tin}`);
        } else if (roles.includes('ROLE_USER')) {
          navigate(`/api/v2/propertyowner/${tin}`);
        } else {
          navigate('/');
        }
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

  useEffect(() => {
    // Cleanup function to reset isSubmitting when component is unmounted or refreshed
    return () => {
      setIsSubmitting(false);
    };
  }, []);

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
