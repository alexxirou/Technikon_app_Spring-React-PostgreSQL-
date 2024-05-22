
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import { AuthProvider } from './context/AuthContext';
import MainLayout from './layouts/MainLayout';
import Home from './views/Home/Home';
import Login from './views/Login/Login';
import PropertyOwnerSignUp from './views/SignUp/PropertyOwnerSignUp';
import Repair from './views/Repair/Repair';
import CreateRepair from './views/Repair/CreateRepair';
import Admin from './views/Admin';
import Owner from './views/Owner';
import RoleProtectedRoute from './components/RoleProtectedRoute';

const App = () => {
  return (
    <Router>
      <AuthProvider>
        <Routes>
          <Route element={<MainLayout />}>
            <Route path="/" element={<Home />} />
            <Route path="/login" element={<Login />} />
            <Route path="/signup" element={<PropertyOwnerSignUp />} />
            <Route path="/api/repair" element={<Repair />} />
            <Route path="/create-repair" element={<CreateRepair />} />
            <Route
              path="/api/admin"
              element={
                <RoleProtectedRoute allowedRoles={['ROLE_ADMIN']}>
                  <Admin />
                </RoleProtectedRoute>
              }
            />
            <Route
              path="/api/owner"
              element={
                <RoleProtectedRoute allowedRoles={['ROLE_USER', 'ROLE_ADMIN']}>
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

