import React from 'react';
import Button from "@mui/material/Button";
import { Link } from "react-router-dom";
import { PATHS, ROLES } from "../lib/constants";

const AuthButtons = ({ authData, logout }) => {
  return (
    <>
      {authData ? (
        <>
          <Button component={Link} to={PATHS.OWNER(authData.userId)} color="inherit">
            Owner
          </Button>
          <Button component={Link} to={PATHS.SHOW_PROPERTIES(authData.userId)} color="inherit">
            Properties
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
    </>
  );
};

export default AuthButtons;
