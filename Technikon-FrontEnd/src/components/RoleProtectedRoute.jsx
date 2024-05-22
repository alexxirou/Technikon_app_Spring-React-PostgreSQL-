import PropTypes from 'prop-types';
import { Navigate } from 'react-router-dom';
import { useAuth } from '../hooks/useAuth';

const RoleProtectedRoute = ({ allowedRoles, children }) => {
  const { authData } = useAuth();
  const isAuthenticated = !!authData;

  // If authentication data is not available, return a loading indicator or null
  if (!isAuthenticated) {
    return null; // Or return a loading indicator if needed
  }

  const userRoles = authData.authorities || [];
  const hasRequiredRole = allowedRoles.some(role => userRoles.includes(role));

  if (!hasRequiredRole) {
    return <Navigate to="/" replace />;
  }

  return children;
};

RoleProtectedRoute.propTypes = {
  allowedRoles: PropTypes.arrayOf(PropTypes.string).isRequired,
  children: PropTypes.node.isRequired,
};

export default RoleProtectedRoute;
