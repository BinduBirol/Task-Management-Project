import { Box, List, ListItem, ListItemText } from "@mui/material";
import { useNavigate } from "react-router-dom";

export default function SideNav() {
    const navigate = useNavigate();

    return (
        <Box sx={{ width: 200, height: "100vh", bgcolor: "background.paper" }}>
            <List>

                <ListItem button onClick={() => navigate("/dashboard")}>
                    <ListItemText primary="Dashboard" />
                </ListItem>

                <ListItem button onClick={() => navigate("/profile")}>
                    <ListItemText primary="Profile" />
                </ListItem>

            </List>
        </Box>
    );
}