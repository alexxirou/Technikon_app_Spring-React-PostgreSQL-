import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import MainLayout from "./layouts/MainLayout";
import Home from "./views/Home/Home";
import Admin from "./views/Admin";
import Owner from "./views/Owner";
import Login from "./views/Login/Login";
import RoleProtectedRoute from './components/RoleProtectedRoute';

function App() {
  return (
    <Router>
      <Routes>
        <Route element={<MainLayout />}>
          <Route path="/" element={<Home />} />
          <Route path="/login" element={<Login />} />
          <Route
            path="/api/v2/admin"
            element={
              <RoleProtectedRoute allowedRoles={['ROLE_ADMIN']}>
                <Admin />
              </RoleProtectedRoute>
            }
          />
          <Route
            path="/api/v2/propertyOwners"
            element={
              <RoleProtectedRoute allowedRoles={['ROLE_USER', 'ROLE_ADMIN']}>
                <Owner />
              </RoleProtectedRoute>
            }
          />
          <Route
          //   path="/api/v2/propertyRepairs"
          //   element={
          //     <RoleProtectedRoute allowedRoles={['ROLE_USER', 'ROLE_ADMIN']}>
          //       <PropertyRepair />
          //     </RoleProtectedRoute>
          //   }
          // />
          // <Route
          //   path="/api/v2/properties"
          //   element={
          //     <RoleProtectedRoute allowedRoles={['ROLE_USER', 'ROLE_ADMIN']}>
          //       <Property />
          //     </RoleProtectedRoute>
          //   }
          />
        </Route>
      </Routes>
    </Router>
  );
}

export default App;
