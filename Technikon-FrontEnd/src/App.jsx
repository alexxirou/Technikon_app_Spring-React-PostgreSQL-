
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
import ShowRepairs from './views/Repair/ShowRepairs';
import RepairDetails from './views/Repair/RepairDetails';

const App = () => {

  return (
    <Router>
      <AuthProvider>
        <Routes>
          <Route element={<MainLayout />}>
            <Route path="/" element={<Home />} />
            <Route path="/login" element={<Login />} />
            <Route path="/signup" element={<PropertyOwnerSignUp />} />
            <Route path="/repair/:id" element={<Repair />} />
            <Route path="/create-repair" element={<CreateRepair />} />
            <Route path="/all-by-owner/:propertyOwnerId" element={<ShowRepairs />} />
            <Route path="/:propertyOwnerId/:repairId" element={<RepairDetails />} />
            <Route
              path="/admin"
              element={
                <RoleProtectedRoute allowedRoles={['ROLE_ADMIN']}>
                  <Admin />
                </RoleProtectedRoute>
              }
            />
            <Route
              path={`/owner/:tin`}
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

