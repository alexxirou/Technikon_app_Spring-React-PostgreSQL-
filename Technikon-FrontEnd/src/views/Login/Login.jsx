// src/views/Login/Login.js
import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import jwtDecode from 'jwt-decode';
import './Login.css';

const Login = () => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');
  const navigate = useNavigate();

  const handleSubmit = async (event) => {
    event.preventDefault();

    try {
      const response = await fetch('/api/login', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ username, password }),
      });

      if (!response.ok) {
        const errorMessage = response.headers.get('Error-Message') || 'Login failed';
        throw new Error(errorMessage);
      }

      const data = await response.json();

      if (data.token) {
        localStorage.setItem('token', data.token); // Store the JWT
        const decodedToken = jwtDecode(data.token);
        const roles = decodedToken.authorities || [];
        const tin = decodedToken.claims?.tin; // Extract tin from the claims

        if (roles.includes('ROLE_ADMIN')) {
          navigate(`/api/v2/admin/${tin}`); // Redirect to admin page with tin
        } else if (roles.includes('ROLE_USER')) {
          navigate(`/api/v2/propertyowner/${tin}`); // Redirect to owner page with tin
        } else {
          navigate('/'); // Default redirection
        }
      } else {
        setError('Login failed: Invalid response');
      }
    } catch (error) {
      setError(error.message);
      console.error('Error:', error);
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
        <button type="submit">Login</button>
      </form>
    </div>
  );
};

export default Login;
