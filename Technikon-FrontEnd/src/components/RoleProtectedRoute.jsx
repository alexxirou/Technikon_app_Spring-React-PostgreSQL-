import PropTypes from 'prop-types';
import { Navigate } from 'react-router-dom';
import { useAuth } from '../hooks/useAuth';
import { CircularProgress, Box } from '@mui/material';
import { PATHS } from '../lib/constants'; // Import your paths from constants

const RoleProtectedRoute = ({ allowedRoles, children }) => {
  const { authData } = useAuth();
  const isAuthenticated = !!authData;

  // If authentication data is not available yet, return a loading indicator
  if (authData === null) {
    return (
      <Box display="flex" justifyContent="center" alignItems="center" height="100vh">
        <CircularProgress />
      </Box>
    );
  }

  // If authentication data is not available, redirect to the home page
  if (!isAuthenticated) {
    return <Navigate to={PATHS.HOME} replace />;
  }

  const userRoles = authData.authorities || [];
  const hasRequiredRole = allowedRoles.some(role => userRoles.includes(role));

  // If the user doesn't have the required role, redirect to the home page
  if (!hasRequiredRole) {
    return <Navigate to={PATHS.HOME} replace />;
  }

  // Render the protected route children if the user has the required role and authentication data
  return children;
};

RoleProtectedRoute.propTypes = {
  allowedRoles: PropTypes.arrayOf(PropTypes.string).isRequired,
  children: PropTypes.node.isRequired,
};

export default RoleProtectedRoute;
