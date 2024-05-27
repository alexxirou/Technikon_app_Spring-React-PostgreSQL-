import { useState } from 'react';
import TextField from "@mui/material/TextField";
import Select from "@mui/material/Select";
import MenuItem from "@mui/material/MenuItem";
import Button from "@mui/material/Button";
import Box from "@mui/material/Box";

import api from "../api/Api";
import { useAuth } from '../hooks/useAuth';

const SearchForm = ({ setSearchResult, setSearchDialogOpen, setError }) => {
  const { authData } = useAuth();
  const [searchType, setSearchType] = useState('tin');
  const [searchValue, setSearchValue] = useState('');

  const handleSearch = async () => {
    const searchObject = {
      tin: null,
      username: null,
      email: null,
    };

    searchObject[searchType] = searchValue.trim() === '' ? null : searchValue;

    try {
      const response = await api.get('/api/propertyOwners/', { params: searchObject });
      if (response.status === 200) {
        const data = response.data;
        setSearchResult(data);
        setSearchDialogOpen(true);
      } else {
        const errorMessage = response.data.message || 'Login failed: Invalid response';
        throw new Error(errorMessage);
      }
    } catch (error) {
      console.error('Error:', error);
      if (error.response) {
        const errorMessage = error.response.data || 'Login failed: Invalid response';
        setError(errorMessage);
      } else if (error.request) {
        setError('No response received from the server');
      } else {
        setError('An unexpected error occurred');
      }
    }
  };

  return (
    <Box>
      {authData && (
        <>
          <Select
            value={searchType}
            onChange={(e) => setSearchType(e.target.value)}
            size="small"
            sx={{ mr: 2 }}
          >
            <MenuItem value="tin">TIN</MenuItem>
            <MenuItem value="username">Username</MenuItem>
            <MenuItem value="email">Email</MenuItem>
          </Select>
          <TextField
            label="Search"
            variant="outlined"
            size="small"
            value={searchValue}
            onChange={(e) => setSearchValue(e.target.value)}
            InputProps={{
              sx: {
                backgroundColor: 'white',
              },
            }}
            sx={{ mr: 2 }}
          />
          <Button variant="contained" color="primary" onClick={handleSearch}>
            Search
          </Button>
        </>
      )}
    </Box>
  );
};

export default SearchForm;
