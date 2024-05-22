import { Link } from "react-router-dom";
import { useState, useEffect } from "react";
import AppBar from "@mui/material/AppBar";
import Box from "@mui/material/Box";
import Toolbar from "@mui/material/Toolbar";
import Typography from "@mui/material/Typography";
import IconButton from "@mui/material/IconButton";
import MenuIcon from "@mui/icons-material/Menu";
import Button from "@mui/material/Button";

import { useAuth } from '../hooks/useAuth';

const Header = () => {



  const { isLoggedIn, handleLogout, authorities } = useAuth();


  
  

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
          {isLoggedIn && authorities.includes('ROLE_ADMIN') && (
            <Typography
              variant="h6"
              component={Link}
              to="/admin"
              sx={{
                flexGrow: 1,
                textDecoration: "none",
                color: "inherit",
              }}
            >
              Admin
            </Typography>
          )}
          {isLoggedIn && (
            <Typography
              variant="h6"
              component={Link}
              to="/owner"
              sx={{
                flexGrow: 1,
                textDecoration: "none",
                color: "inherit",
              }}
            >
              Owner
            </Typography>
          )}
          {isLoggedIn && (
            <Typography
              variant="h6"
              component={Link}
              to="/api/repair"
              sx={{
                flexGrow: 1,
                textDecoration: "none",
                color: "inherit",
              }}
            >
              Repair
            </Typography>
          )}
          {isLoggedIn ? (
            <Button color="inherit" onClick={handleLogout}>
              Logout
            </Button>
          ) : (
            <>
              <Typography
                variant="h6"
                component={Link}
                to="/signup"
                sx={{
                  textDecoration: "none",
                  color: "inherit",
                  mr: 2,
                }}
              >
                Sign Up
              </Typography>
              <Typography
                variant="h6"
                component={Link}
                to="/login"
                sx={{
                  textDecoration: "none",
                  color: "inherit",
                }}
              >
                Login
              </Typography>
            </>
          )}
        </Toolbar>
      </AppBar>
    </Box>
  );
};

export default Header;
