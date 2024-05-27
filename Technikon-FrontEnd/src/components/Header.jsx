import { useState } from 'react';
import { Link } from "react-router-dom";
import AppBar from "@mui/material/AppBar";
import Box from "@mui/material/Box";
import Toolbar from "@mui/material/Toolbar";
import Typography from "@mui/material/Typography";
import Button from "@mui/material/Button";
import TextField from "@mui/material/TextField";
import Select from "@mui/material/Select";
import MenuItem from "@mui/material/MenuItem";
import Dialog from "@mui/material/Dialog";
import DialogTitle from "@mui/material/DialogTitle";
import DialogContent from "@mui/material/DialogContent";
import DialogActions from "@mui/material/DialogActions";
import Card from "@mui/material/Card";
import CardContent from "@mui/material/CardContent";
import { PATHS, ROLES } from "../lib/constants";
import { useAuth } from '../hooks/useAuth';
import LogoutDialog from "../components/LogoutDialog";
import api from "../api/Api";

const Header = () => {
  const { authData, logout, logoutDialogOpen, handleCloseLogoutDialog } = useAuth();
  const [searchType, setSearchType] = useState('tin');
  const [searchValue, setSearchValue] = useState('');
  const [searchResult, setSearchResult] = useState([]);
  const [searchDialogOpen, setSearchDialogOpen] = useState(false);

  const handleSearch = async () => {
    const searchObject = {
      tin: null,
      username: null,
      email: null,
    };

    searchObject[searchType] = searchValue.trim() === '' ? null : searchValue;

    try {
      const response = await api.get('/api/propertyOwners/', { params: searchObject });
      if (response.status===200){
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

  const handleCloseSearchDialog = () => {
    setSearchDialogOpen(false);
  };

  const handleGoToOwnerDetails = (id) => {
    setSearchDialogOpen(false); 
    window.location.href = PATHS.OWNER(id);
  };

  return (
    <Box sx={{ flexGrow: 1 }}>
      <AppBar position="static">
        <Toolbar>
          <Typography
            variant="h6"
            component={Link}
            to={PATHS.HOME}
            sx={{
              flexGrow: 1,
              textDecoration: "none",
              color: "inherit",
            }}
          >
            Home
          </Typography>
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
          {authData && authData.authorities.includes(ROLES.ADMIN) && (
            <Button component={Link} to={PATHS.ADMIN} color="inherit">
              Admin
            </Button>
          )}
          {authData ? (
            <>
              <Button component={Link} to={PATHS.OWNER(authData.userId)} color="inherit">
                Owner
              </Button>
              <Button component={Link} to={PATHS.SHOW_REPAIRS(authData.userId)} color="inherit">
                Repairs
              </Button>
              <Button color="inherit" onClick={logout}>
                Logout
              </Button>
            </>
          ) : (
            <>
              <Button component={Link} to={PATHS.SIGNUP} color="inherit">
                Sign Up
              </Button>
              <Button component={Link} to={PATHS.LOGIN} color="inherit">
                Login
              </Button>
            </>
          )}
        </Toolbar>
      </AppBar>
      <LogoutDialog
        open={logoutDialogOpen}
        onClose={handleCloseLogoutDialog}
        onConfirm={handleCloseLogoutDialog}
      />
      <Dialog open={searchDialogOpen} onClose={handleCloseSearchDialog} fullWidth maxWidth="sm">
        <DialogTitle>Search Result</DialogTitle>
        <DialogContent sx={{ display: 'flex', flexDirection: 'column', alignItems: 'center' }}>
          {searchResult.map(result => (
            <Box key={result.tin} sx={{ mb: 2, width: '100%' }}>
              <Card>
                <CardContent>
                  <Typography variant="h6">Search Result</Typography>
                  <Typography><strong>TIN:</strong> {result.tin}</Typography>
                  <Typography><strong>Username:</strong> {result.username}</Typography>
                  <Typography><strong>Email:</strong> {result.email}</Typography>
                </CardContent>
              </Card>
              {authData && authData.authorities.includes(ROLES.ADMIN) && (
                <Box sx={{ display: 'flex', justifyContent: 'center', marginTop: '1rem' }}>
                  <Button
                    onClick={() => handleGoToOwnerDetails(result.id)}
                    color="primary"
                    variant="contained"
                  >
                    Go to Owner Details
                  </Button>
                </Box>
              )}
            </Box>
          ))}
        </DialogContent>
        <DialogActions>
          <Button onClick={handleCloseSearchDialog} color="primary">
            Close
          </Button>
        </DialogActions>
      </Dialog>
    </Box>
  );
};

export default Header;
