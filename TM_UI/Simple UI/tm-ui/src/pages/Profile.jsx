import {
    Box,
    Typography,
    Card,
    CardContent,
    Avatar,
    Divider
} from "@mui/material";

import DashboardLayout from "../layout/DashboardLayout";

export default function Profile() {

    // dummy data for now
    const user = {
        name: "John Doe",
        email: "john@example.com",
        role: "User"
    };

    return (
        <DashboardLayout>
            <Box sx={{ maxWidth: 500, mx: "auto", mt: 4 }}>

                <Card sx={{ p: 2, borderRadius: 3 }}>
                    <CardContent>

                        {/* Avatar */}
                        <Box sx={{ display: "flex", justifyContent: "center", mb: 2 }}>
                            <Avatar sx={{ width: 80, height: 80 }}>
                                {user.name[0]}
                            </Avatar>
                        </Box>

                        {/* Title */}
                        <Typography variant="h6" align="center" gutterBottom>
                            Profile
                        </Typography>

                        <Divider sx={{ mb: 2 }} />

                        {/* Info */}
                        <Typography sx={{ mb: 1 }}>
                            <strong>Name:</strong> {user.name}
                        </Typography>

                        <Typography sx={{ mb: 1 }}>
                            <strong>Email:</strong> {user.email}
                        </Typography>

                        <Typography>
                            <strong>Role:</strong> {user.role}
                        </Typography>

                    </CardContent>
                </Card>

            </Box>
        </DashboardLayout>
    );
}