import { Box } from "@mui/material";
import TopNav from "./TopNav";
import SideNav from "./SideNav";

export default function DashboardLayout({ children }) {
    return (
        <Box>

            {/* Top navigation */}
            <TopNav />

            <Box sx={{ display: "flex" }}>

                {/* Side navigation */}
                <SideNav />

                {/* Main content */}
                <Box sx={{ flex: 1, p: 2 }}>
                    {children}
                </Box>

            </Box>
        </Box>
    );
}