import  { useState } from 'react';
import { Link } from "react-router-dom";
import AppBar from "@mui/material/AppBar";
import Toolbar from "@mui/material/Toolbar";
import Typography from "@mui/material/Typography";
import Box from "@mui/material/Box";
import LogoutDialog from "../components/LogoutDialog";
import Dialog from "@mui/material/Dialog";
import DialogTitle from "@mui/material/DialogTitle";
import DialogContent from "@mui/material/DialogContent";
import DialogActions from "@mui/material/DialogActions";
import Card from "@mui/material/Card";
import CardContent from "@mui/material/CardContent";
import Button from "@mui/material/Button";
import { PATHS, ROLES } from "../lib/constants";
import { useAuth } from '../hooks/useAuth';
import AuthButtons from './AuthButtons';
import SearchForm from './SearchForm';


const Header = () => {
  const { authData, logout, logoutDialogOpen, handleCloseLogoutDialog } = useAuth();
  const [searchResult, setSearchResult] = useState([]);
  const [searchDialogOpen, setSearchDialogOpen] = useState(false);
  const [error, setError] = useState('');

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
            TECHNIKON
          </Typography>
          <SearchForm
            setSearchResult={setSearchResult}
            setSearchDialogOpen={setSearchDialogOpen}
            setError={setError}
          />
          <AuthButtons authData={authData} logout={logout} />
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
        <DialogContent sx={{ display: 'flex', flexDirection: 'column', alignItems: 'center' }}>
          {error && (
            <Typography variant="body1" color="error" sx={{ mt: 2 }}>
              {error}
            </Typography>
          )}
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
