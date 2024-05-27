import { Link } from "react-router-dom";
import AppBar from "@mui/material/AppBar";
import Box from "@mui/material/Box";
import Toolbar from "@mui/material/Toolbar";
import Typography from "@mui/material/Typography";
import IconButton from "@mui/material/IconButton";
import MenuIcon from "@mui/icons-material/Menu";
import Button from "@mui/material/Button";
import { PATHS, ROLES } from "../lib/constants";
import { useAuth } from '../hooks/useAuth';
import LogoutDialog from "../components/LogoutDialog";

const Header = () => {
  const { authData, logout, logoutDialogOpen, handleCloseLogoutDialog } = useAuth();

  return (
    <Box sx={{ flexGrow: 1 }}>
      <AppBar position="static">
        <Toolbar>
          <IconButton
            size="large"
            edge="start"
            color="inherit"
            aria-label="open navigation menu"
            sx={{ mr: 2 }}
          >
            <MenuIcon />
          </IconButton>
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
          <Button variant="contained" color="primary" onClick={handleSearch}>
            Search
          </Button>
          <TextField
            label="Search"
            variant="outlined"
            size="small"
            value={searchValue}
            onChange={(e) => setSearchValue(e.target.value)}
            InputProps={{ sx: { backgroundColor: 'white' } }}

            sx={{ mr: 2, ml:2 }}
          />




<Select
  value={searchType}
  onChange={(e) => setSearchType(e.target.value)}
  size="small"

  sx={{
    mr: 2,
  }}
>
  <MenuItem value="tin">TIN</MenuItem>
  <MenuItem value="username">Username</MenuItem>
  <MenuItem value="email">Email</MenuItem>
</Select>




          {authData ? (
            <>
              {authData.authorities.includes(ROLES.ADMIN) && (
                <Button component={Link} to={PATHS.ADMIN} color="inherit">
                  Admin
                </Button>
              )}
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
              <Box sx={{ display: 'flex', justifyContent: 'center', marginTop: '1rem' }}>
                <Button
                  onClick={() => handleGoToOwnerDetails(result.id)}
                  color="primary"
                  variant="contained"
                >
                  Go to Owner Details
                </Button>
              </Box>
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
