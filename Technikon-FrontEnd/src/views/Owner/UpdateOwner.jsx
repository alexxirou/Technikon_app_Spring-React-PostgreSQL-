import { useState } from 'react';
import { useAuth } from '../../hooks/useAuth';
import { PATHS } from '../../lib/constants';
import api from '../../api/Api';
import { useNavigate } from 'react-router-dom';

const UpdateOwner = () => {
  const { authData } = useAuth();
  const [errors, setErrors] = useState({});
  const tin = authData?.userTin;
  const [success, setSuccess] = useState(false);
  const [email, setEmail] = useState('');
  const [address, setAddress] = useState('');
  const [password, setPassword] = useState('');
  const navigate = useNavigate(); // Import and use useNavigate hook

  const handleSubmit = async (e, submitField) => {
    e.preventDefault();

    // Basic validation
    const newErrors = {};
    if (email && !isValidEmail(email)) {
      newErrors.email = 'Invalid email format';
    }
    if (address && address.length < 5) {
      newErrors.address = 'Address should be at least 5 characters long';
    }
    if (password && !isValidPassword(password)) {
      newErrors.password = 'Password should be at least 8 characters long and contain at least one uppercase letter, one lowercase letter, one digit, and one special character.';
    }

    setErrors(newErrors);

    if (Object.keys(newErrors).length > 0) {
      // If there are validation errors, return without submitting the form
      return;
    }

    try {
      // Make a PUT request to update the owner
      const response = await api.put(`/api/propertyOwners/${tin}`, { email, address, password });
      // Redirect to owner profile page after successful update
      console.log(response.status);
      setSuccess(true); // Show the success dialog
      setTimeout(() => {
        navigate(PATHS.OWNER(tin)); // Redirect to owner profile page
      }, 3000); // Redirect after 3 seconds (3000 milliseconds)
    } catch (error) {
      console.error('Error:', error);
      if (error.response) {
        const errorMessage = error.response.data;
        setErrors(errorMessage);
      } else if (error.request) {
        setErrors('No response received from the server');
      } else {
        setErrors('An unexpected error occurred');
      }
    }
  };

  const isValidEmail = (email) => {
    // Basic email validation regex
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return emailRegex.test(email);
  };

  const isValidPassword = (password) => {
    const pattern = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[\W_]).{8,}$/;
    return pattern.test(password);
  };

  return (
    <div>
      <h2>Update Owner</h2>
      {success && (
        <div className="success-dialog">
          Owner details updated successfully. Redirecting...
        </div>
      )}
      <form>
        <div>
          <label htmlFor="email">Email Address:</label>
          <input
            type="email"
            id="email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
          />
          {errors.email && <div className="error">{errors.email}</div>}
        </div>
        <div>
          <label htmlFor="address">Address:</label>
          <input
            type="text"
            id="address"
            value={address}
            onChange={(e) => setAddress(e.target.value)}
          />
          {errors.address && <div className="error">{errors.address}</div>}
        </div>
        <div>
          <label htmlFor="password">Password:</label>
          <input
            type="password"
            id="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
          />
          {errors.password && <div className="error">{errors.password}</div>}
        </div>
        <button type="submit" onClick={(e) => handleSubmit(e)}>Update All</button>
        <button type="submit" onClick={(e) => handleSubmit(e, 'email')}>Update Email</button>
        <button type="submit" onClick={(e) => handleSubmit(e, 'address')}>Update Address</button>
        <button type="submit" onClick={(e) => handleSubmit(e, 'password')}>Update Password</button>
      </form>
    </div>
  );
};

export default UpdateOwner;
