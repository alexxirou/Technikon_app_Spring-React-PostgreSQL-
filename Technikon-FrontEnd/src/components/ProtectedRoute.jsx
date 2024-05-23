import PropTypes from 'prop-types';
import { Route } from 'react-router-dom';
import RoleProtectedRoute from './RoleProtectedRoute'; // Assuming RoleProtectedRoute is correctly imported

const ProtectedRoute = ({ path, element, allowedRoles }) => (
  <Route
    path={path}
    element={
      <RoleProtectedRoute allowedRoles={allowedRoles}>
        {element}
      </RoleProtectedRoute>
    }
  />
);

ProtectedRoute.propTypes = {
  path: PropTypes.string.isRequired,
  element: PropTypes.node.isRequired, // Updated to PropTypes.node
  allowedRoles: PropTypes.arrayOf(PropTypes.string).isRequired,
};

export default ProtectedRoute;
