
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import { AuthProvider } from './context/AuthContext';
import MainLayout from './layouts/MainLayout';
import Home from './views/Home/Home';
import Login from './views/Login/Login';
import PropertyOwnerSignUp from './views/SignUp/PropertyOwnerSignUp';
import Repair from './views/Repair/Repair';
import CreateRepair from './views/Repair/CreateRepair';
import Admin from './views/Admin';
import Owner from './views/Owner/Owner';
import RoleProtectedRoute from './components/RoleProtectedRoute';
import { PATHS, ROLES } from './lib/constants';
const App = () => {
  return (
    <Router>
      <AuthProvider>
        <Routes>
          <Route element={<MainLayout />}>
            <Route path={PATHS.HOME} element={<Home />} />
            <Route path={PATHS.LOGIN} element={<Login />} />
            <Route path={PATHS.SIGNUP} element={<PropertyOwnerSignUp />} />
            <Route path={PATHS.REPAIR} element={<Repair />} />
            <Route path={PATHS.CREATE_REPAIR} element={<CreateRepair />} />
            <Route
              path={PATHS.ADMIN}
              element={
                <RoleProtectedRoute allowedRoles={[ROLES.ADMIN]}>
                  <Admin />
                </RoleProtectedRoute>
              }
            />
            <Route
              path={PATHS.OWNER(':tin')}
              element={
                <RoleProtectedRoute allowedRoles={[ROLES.USER, ROLES.ADMIN]}>
                  <Owner />
                </RoleProtectedRoute>
              }
            />
          </Route>
        </Routes>
      </AuthProvider>
    </Router>
  );
};

export default App;

