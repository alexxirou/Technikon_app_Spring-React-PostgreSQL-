import PropTypes from 'prop-types';
import { Navigate } from 'react-router-dom';
import { jwtDecode } from "jwt-decode";

const RoleProtectedRoute = ({ children, allowedRoles }) => {
  const token = localStorage.getItem('token');

  if (!token) {
    return <Navigate to="/login" />;
  }

  const decodedToken = jwtDecode(token);
  const { authorities: authoritiesArray } = decodedToken;
  const roles = authoritiesArray.map(authority => authority.authority);

  if (roles.some(role => allowedRoles.includes(role))) {
    return children;
  }

  return <Navigate to="/" />;
};

RoleProtectedRoute.propTypes = {
  children: PropTypes.node.isRequired,
  allowedRoles: PropTypes.arrayOf(PropTypes.string).isRequired
};

export default RoleProtectedRoute;