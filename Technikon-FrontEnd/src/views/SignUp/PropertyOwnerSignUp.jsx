import { useEffect, useState } from 'react';
import axios from 'axios';

const Signup = () => {
  const [tin, setTin] = useState('');
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [email, setEmail] = useState('');
  const [name, setName] = useState('');
  const [surname, setSurname] = useState('');
  const [address, setAddress] = useState('');
  const [phoneNumber, setPhoneNumber] = useState('');
  const [error, setError] = useState('');
  const [isSubmitting, setIsSubmitting] = useState(false);
  const [success, setSuccess] = useState('');

  useEffect(() => {
    const isSubmittingStored = localStorage.getItem('isSubmitting');
    if (isSubmittingStored) {
      setIsSubmitting(JSON.parse(isSubmittingStored));
    }
  }, []);

  // Function to validate email format
  const validateEmail = (email) => {
    const pattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return pattern.test(email);
  };

  // Function to validate password strength
  const validatePassword = (password) => {
    // Password validation regex pattern
    const pattern = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/;
    return pattern.test(password);
  };

  // Function to validate TIN format
  const validateTin = (tin) => {
    // TIN validation regex pattern
    const pattern = /^[a-zA-Z0-9]{9,}$/;
    return pattern.test(tin);
  };

  // Function to validate phone number format with country code
  const validatePhoneNumber = (phoneNumber) => {
    // Phone number validation regex pattern with country code
    const pattern = /^\+[1-9]\d{1,14}$/;
    return pattern.test(phoneNumber);
  };

  const handleSubmit = async (event) => {
    event.preventDefault();

    if (isSubmitting) {
      return;
    }

    try {
      setIsSubmitting(true);

      if (!validateTin(tin)) {
        throw new Error('TIN must be at least 9 characters long and consist of alphanumeric characters');
      }

      if (username.length < 5) {
        throw new Error('Username must be at least 5 characters long');
      }

      if (!validatePassword(password)) {
        throw new Error('Password must contain at least one uppercase letter, one lowercase letter, one number, one special character, and be at least 8 characters long');
      }

      if (!validateEmail(email)) {
        throw new Error('Invalid email format');
      }

      if (!validatePhoneNumber(phoneNumber)) {
        throw new Error('Phone number must be 10 digits long and have a country code');
      }

      const response = await axios.post('http://localhost:5001/signup', {
        tin,
        name,
        surname,
        email,
        username,
        password,
        address,
        phoneNumber,
      });

      if (response.status === 201) {
        setSuccess('Signup successful!');
        setTin('');
        setUsername('');
        setPassword('');
        setEmail('');
        setName('');
        setSurname('');
        setAddress('');
        setPhoneNumber('');
        setError('');
      } else {
        setError('Signup failed: Invalid response');
      }
    } catch (error) {
      setError(error.message || 'An unexpected error occurred');
      console.error('Error:', error);
    } finally {
      setIsSubmitting(false);
    }
  };

  return (
    <div className="signup-container">
      <h2>Signup</h2>
      {error && <p className="error-message">{error}</p>}
      {success && <p className="success-message">{success}</p>}
      <form onSubmit={handleSubmit}>
        <div className="form-group">
          <label htmlFor="tin">TIN:</label>
          <input
            type="text"
            id="tin"
            value={tin}
            onChange={(e) => setTin(e.target.value)}
            required
          />
          {!validateTin(tin) && (
            <p className="validation-message">TIN must be at least 9 characters long and consist of alphanumeric characters</p>
          )}
        </div>
        <div className="form-group">
          <label htmlFor="username">Username:</label>
          <input
            type="text"
            id="username"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
            required
          />
          {username.length < 5 && (
            <p className="validation-message">Username must be at least 5 characters long</p>
          )}
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
          {!validatePassword(password) && (
            <p className="validation-message">Password must contain at least one uppercase letter, one lowercase letter, one number, one special character, and be at least 8 characters long</p>
          )}
        </div>
        <div className="form-group">
          <label htmlFor="email">Email:</label>
          <input
            type="email"
            id="email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            required
          />
          {!validateEmail(email) && (
            <p className="validation-message">Invalid email format</p>
          )}
        </div>
        <div className="form-group">
          <label htmlFor="name">Name:</label>
          <input
            type="text"
            id="name"
            value={name}
            onChange={(e) => setName(e.target.value)}
            required
          />
        </div>
        <div className="form-group">
          <label htmlFor="surname">Surname:</label>
          <input
            type="text"
            id="surname"
            value={surname}
            onChange={(e) => setSurname(e.target.value)}
            required
          />
        </div>
        <div className="form-group">
          <label htmlFor="address">Address:</label>
          <input
            type="text"
            id="address"
            value={address}
            onChange={(e) => setAddress(e.target.value)}
            required
          />
        </div>
        <div className="form-group">
          <label htmlFor="phoneNumber">Phone Number:</label>
          <input
            type="text"
            id="phoneNumber"
            value={phoneNumber}
            onChange={(e) => setPhoneNumber(e.target.value)}
            required
          />
          {!validatePhoneNumber(phoneNumber) && (
            <p className="validation-message">Invalid phone number format</p>
          )}
        </div>
        <button type="submit" disabled={isSubmitting}>Signup</button>
      </form>
    </div>
  );
};

export default Signup;
