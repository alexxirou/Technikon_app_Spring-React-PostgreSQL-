import { Link } from "react-router-dom";
import { useState, useEffect } from "react";
import AppBar from "@mui/material/AppBar";
import Box from "@mui/material/Box";
import Toolbar from "@mui/material/Toolbar";
import Typography from "@mui/material/Typography";
import IconButton from "@mui/material/IconButton";
import MenuIcon from "@mui/icons-material/Menu";
import Button from "@mui/material/Button";
import { useNavigate } from 'react-router-dom';
import { useAuth } from '../hooks/useAuth';
import { jwtDecode } from "jwt-decode";
const Header = () => {
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [authorities, setAuthorities] = useState([]);
  const navigate = useNavigate();
  const { authData, setAuthData } = useAuth();

  useEffect(() => {
    // Read token and authData from localStorage on component mount
    const token = localStorage.getItem("token");
    const decodedToken = jwtDecode(token);
        
    const { tin, id, username, authorities: authoritiesArray, exp } = decodedToken;
    const authorities = authoritiesArray.map(authority => authority.authority);
    setAuthData({ userId:id, userTin:tin, username:username, authorities: authorities, expDate:exp });

    if (authData && token) {
      const isTokenExpired = isExpired(authData.expDate);
      if (isTokenExpired) {
        clearToken();
      } else {
        setIsLoggedIn(true);
        setAuthorities(authData.authorities || []);
      }
    }
  }, []);

  useEffect(() => {
    if (authData) {
      const isTokenExpired = isExpired(authData.expDate);
      if (isTokenExpired) {
        clearToken();
      } else {
        setIsLoggedIn(true);
        setAuthorities(authData.authorities || []);
      }
    }
  }, [authData]);

  const isExpired = (exp) => {
    return exp < Date.now() / 1000;
  };

  const clearToken = () => {
    localStorage.removeItem("authData");
    localStorage.removeItem("token");
    setIsLoggedIn(false);
    navigate('/');
  };

  const handleLogout = () => {
    localStorage.removeItem("authData");
    localStorage.removeItem("token");
    setIsLoggedIn(false);
    navigate('/');
  };

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
