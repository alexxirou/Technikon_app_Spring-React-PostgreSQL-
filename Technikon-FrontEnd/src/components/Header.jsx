import { Link } from "react-router-dom";
import AppBar from "@mui/material/AppBar";
import Box from "@mui/material/Box";
import Toolbar from "@mui/material/Toolbar";
import Typography from "@mui/material/Typography";
import IconButton from "@mui/material/IconButton";
import MenuIcon from "@mui/icons-material/Menu";

const Header = () => {
  return (
    <Box sx={{ flexGrow: 1 }}>
      <AppBar position="static">
        <Toolbar>
          <IconButton
            size="large"
            edge="start"
            color="inherit"
            aria-label="menu"
            sx={{ mr: 2 }}
          >
            <MenuIcon />
          </IconButton>
          <Typography variant="h6" component={Link} to="/" sx={{ flexGrow: 1, textDecoration: 'none', color: 'inherit' }}>
            Home
          </Typography>
          <Typography variant="h6" component={Link} to="/admin" sx={{ flexGrow: 1, textDecoration: 'none', color: 'inherit' }}>
            Admin
          </Typography>
          <Typography variant="h6" component={Link} to="/owner" sx={{ flexGrow: 1, textDecoration: 'none', color: 'inherit' }}>
            Owner
          </Typography>
          <Typography variant="h6" component={Link} to="/repair" sx={{ flexGrow: 1, textDecoration: 'none', color: 'inherit' }}>
            Repairs
          </Typography>
          <Typography variant="h6" component={Link} to="/login" sx={{ textDecoration: 'none', color: 'inherit' }}>
            Login
          </Typography>
        </Toolbar>
      </AppBar>
    </Box>
  );
};

export default Header;
