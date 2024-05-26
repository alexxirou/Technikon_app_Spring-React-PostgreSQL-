// src/components/SignupForm.js
import React from 'react';
import { TextField, Button, Box } from '@mui/material';
import ValidationMessage from './ValidationMessage';

const SignupForm = ({ formData, handleChange, handleSubmit, isFormValid, isSubmitting }) => {
  const { tin, username, password, email, name, surname, address, phoneNumber } = formData;

  const validateTin = (tin) => /^[a-zA-Z0-9]{9,}$/.test(tin);
  const validatePassword = (password) => /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[\W_]).{8,}$/.test(password);
  const validateEmail = (email) => /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email);
  const validatePhoneNumber = (phoneNumber) => /^\+[1-9]\d{1,14}$/.test(phoneNumber);

  return (
    <form onSubmit={handleSubmit}>
      <div className="form-group">
        <TextField
          label="TIN"
          type="text"
          fullWidth
          value={tin}
          onChange={(e) => handleChange('tin', e.target.value)}
          required
          error={!validateTin(tin)}
          helperText={!validateTin(tin) && 'TIN must be at least 9 characters long and consist of alphanumeric characters'}
        />
      </div>
      <div className="form-group">
        <TextField
          label="Username"
          type="text"
          fullWidth
          value={username}
          onChange={(e) => handleChange('username', e.target.value)}
          required
          error={username.length < 5}
          helperText={username.length < 5 && 'Username must be at least 5 characters long'}
        />
      </div>
      <div className="form-group">
        <TextField
          label="Password"
          type="password"
          fullWidth
          value={password}
          onChange={(e) => handleChange('password', e.target.value)}
          required
          error={!validatePassword(password)}
          helperText={!validatePassword(password) && 'Password must contain at least one uppercase letter, one lowercase letter, one number, one special character, and be at least 8 characters long'}
        />
      </div>
      <div className="form-group">
        <TextField
          label="Email"
          type="email"
          fullWidth
          value={email}
          onChange={(e) => handleChange('email', e.target.value)}
          required
          error={!validateEmail(email)}
          helperText={!validateEmail(email) && 'Invalid email format'}
        />
      </div>
      <div className="form-group">
        <TextField
          label="Name"
          type="text"
          fullWidth
          value={name}
          onChange={(e) => handleChange('name', e.target.value)}
          required
        />
      </div>
      <div className="form-group">
        <TextField
          label="Surname"
          type="text"
          fullWidth
          value={surname}
          onChange={(e) => handleChange('surname', e.target.value)}
          required
        />
      </div>
      <div className="form-group">
        <TextField
          label="Address"
          type="text"
          fullWidth
          value={address}
          onChange={(e) => handleChange('address', e.target.value)}
          required
        />
      </div>
      <div className="form-group">
        <TextField
          label="Phone Number"
          type="text"
          fullWidth
          value={phoneNumber}
          onChange={(e) => handleChange('phoneNumber', e.target.value)}
          required
          error={!validatePhoneNumber(phoneNumber)}
          helperText={!validatePhoneNumber(phoneNumber) && 'Phone number must be in the format +<country code><number> and contain 10-15 digits'}
        />
      </div>
      <Button type="submit" variant="contained" color="primary" fullWidth disabled={isSubmitting || !isFormValid()}>
        Signup
      </Button>
    </form>
  );
};

export default SignupForm;
