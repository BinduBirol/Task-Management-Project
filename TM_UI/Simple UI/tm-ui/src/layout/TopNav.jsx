import {
    AppBar,
    Toolbar,
    Typography,
    Button,
    Menu,
    MenuItem,
    IconButton
} from "@mui/material";

import Brightness6Icon from "@mui/icons-material/Brightness6";
import { getStoredThemeMode } from "../configs/themeMode";
import CheckIcon from "@mui/icons-material/Check";
import { useNavigate } from "react-router-dom";
import { useState } from "react";
import { setStoredThemeMode } from "../configs/themeMode";

export default function TopNav() {
    const [anchorEl, setAnchorEl] = useState(null);

    const open = Boolean(anchorEl);

    const navigate = useNavigate();
    const currentMode = getStoredThemeMode();

    const handleOpen = (event) => {
        setAnchorEl(event.currentTarget);
    };

    const handleClose = () => {
        setAnchorEl(null);
    };

    const changeTheme = (mode) => {
        setStoredThemeMode(mode);
        window.location.reload(); // simple refresh to apply theme
    };

    return (
        <AppBar position="static">
            <Toolbar>

                <Typography sx={{ flexGrow: 1 }}>
                    TM UI Dashboard
                </Typography>

                {/* THEME ICON */}
                <IconButton color="inherit" onClick={handleOpen}>
                    <Brightness6Icon />
                </IconButton>

                <Menu anchorEl={anchorEl} open={open} onClose={handleClose}>

                    <MenuItem onClick={() => changeTheme("light")}>
                        {currentMode === "light" && <CheckIcon fontSize="small" sx={{ mr: 1 }} />}
                        Light
                    </MenuItem>

                    <MenuItem onClick={() => changeTheme("dark")}>
                        {currentMode === "dark" && <CheckIcon fontSize="small" sx={{ mr: 1 }} />}
                        Dark
                    </MenuItem>

                    <MenuItem onClick={() => changeTheme("system")}>
                        {currentMode === "system" && <CheckIcon fontSize="small" sx={{ mr: 1 }} />}
                        System
                    </MenuItem>

                </Menu>

                <Button color="inherit" onClick={() => {
                    localStorage.removeItem("token");
                    navigate("/login");
                }}>
                    Logout
                </Button>

            </Toolbar>
        </AppBar>
    );
}