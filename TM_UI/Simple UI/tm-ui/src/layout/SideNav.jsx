import { Box, List, ListItemButton, ListItemText } from "@mui/material";
import { useLocation, useNavigate } from "react-router-dom";

export default function SideNav() {
    const navigate = useNavigate();
    const location = useLocation();

    const menu = [
        { label: "Dashboard", path: "/dashboard" },
        { label: "Profile", path: "/profile" }
    ];

    return (
        <Box
            sx={{
                width: 220,
                height: "100vh",
                bgcolor: "background.paper",
                borderRight: "1px solid",
                borderColor: "divider"
            }}
        >
            <List>

                {menu.map((item) => {
                    const isActive = location.pathname === item.path;

                    return (
                        <ListItemButton
                            key={item.path}
                            onClick={() => navigate(item.path)}
                            sx={{
                                mx: 1,
                                my: 0.5,
                                borderRadius: 2,
                                bgcolor: isActive ? "primary.main" : "transparent",
                                color: isActive ? "white" : "inherit",
                                "&:hover": {
                                    bgcolor: isActive ? "primary.dark" : "action.hover"
                                }
                            }}
                        >
                            <ListItemText primary={item.label} />
                        </ListItemButton>
                    );
                })}

            </List>
        </Box>
    );
}