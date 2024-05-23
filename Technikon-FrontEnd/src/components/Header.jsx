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
            aria-label="menu"
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
          {authData && (
            <>
              {authData.authorities.includes(ROLES.ADMIN) && (
                <Button component={Link} to={PATHS.ADMIN} color="inherit">
                  Admin
                </Button>
              )}
              <Button component={Link} to={PATHS.OWNER(authData.userTin)} color="inherit">
                Owner
              </Button>
              <Button component={Link} to={PATHS.SHOW_REPAIRS(authData.userId)} color="inherit">
                Repairs
              </Button>
              <Button color="inherit" onClick={logout}>
                Logout
              </Button>
            </>
          )}
          {!authData && (
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
    </Box>
  );
};

export default Header;
