
import Button from "@mui/material/Button";
import { Link } from "react-router-dom";
import { PATHS } from "../lib/constants";

const AdminButton = ({ authData }) => {
  return (
    <>
      {authData && authData.authorities.includes(ROLES.ADMIN) && (
        <Button component={Link} to={PATHS.ADMIN} color="inherit">
          Admin
        </Button>
      )}
    </>
  );
};

export default AdminButton;
