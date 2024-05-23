import { Link } from "react-router-dom";

import AppBar from "@mui/material/AppBar";
import Box from "@mui/material/Box";
import Toolbar from "@mui/material/Toolbar";
import Typography from "@mui/material/Typography";
import IconButton from "@mui/material/IconButton";
import MenuIcon from "@mui/icons-material/Menu";
import Button from "@mui/material/Button";
import { PATHS } from "../lib/constants";
import { useAuth } from '../hooks/useAuth';

const Header = () => {
  const { authData, handleLogout } = useAuth();

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
            to="/"
            sx={{
              flexGrow: 1,
              textDecoration: "none",
              color: "inherit",
            }}
          >
            Home
          </Typography>
          {authData && ( // Check if authData exists
            <>
              {authData.authorities.includes('ROLE_ADMIN') && ( // Render admin-related button if user is admin
                <Button
                  component={Link}
                  to="/admin"
                  color="inherit"
                >
                  Admin
                </Button>
              )}
              <Button
                component={Link}
                to={`/owner/${authData.userTin}`}
                color="inherit"
              >
                Owner
              </Button>
              <Button
                component={Link}
                to={PATHS.SHOW_REPAIRS}
                color="inherit"
              >
                Repairs
              </Button>
              <Button color="inherit" onClick={handleLogout}>
                Logout
              </Button>
            </>
          )}
          {!authData && ( // Render signup and login buttons if authData doesn't exist
            <>
              <Button
                component={Link}
                to="/signup"
                color="inherit"
              >
                Sign Up
              </Button>
              <Button
                component={Link}
                to="/login"
                color="inherit"
              >
                Login
              </Button>
            </>
          )}
        </Toolbar>
      </AppBar>
    </Box>
  );
};

export default Header;
