import { useEffect, useState } from "react";
import {
    Box,
    Typography,
    Card,
    CardContent,
    Avatar,
    Divider,
    Skeleton
} from "@mui/material";

import DashboardLayout from "../layout/DashboardLayout";
import { getProfile } from "../auth/authService";

export default function Profile() {

    const [user, setUser] = useState(null);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        const fetchProfile = async () => {
            try {

                const data = await getProfile();
                setUser(data);

            } catch (err) {
                console.error(err);
            } finally {
                setLoading(false);
            }
        };

        fetchProfile();
    }, []);

    return (
        <DashboardLayout>
            <Box sx={{ maxWidth: 520, mx: "auto", mt: 6 }}>

                <Card sx={{
                    borderRadius: 4,
                    boxShadow: 6,
                    p: 2,
                    background: "background.paper"
                }}>
                    <CardContent>

                        {/* Avatar + Name */}
                        <Box sx={{
                            display: "flex",
                            flexDirection: "column",
                            alignItems: "center",
                            mb: 2
                        }}>

                            {loading ? (
                                <Skeleton variant="circular" width={80} height={80} />
                            ) : (
								<Avatar
								    sx={{ width: 80, height: 80, fontSize: 28 }}
								    src={user?.imageUrl || undefined}
								>
								    {!user?.imageUrl && user?.name?.[0]}
								</Avatar>
                            )}

                            <Typography variant="h6" sx={{ mt: 2 }}>
                                {loading ? <Skeleton width={120} /> : user?.name}
                            </Typography>

                            <Typography variant="body2" color="text.secondary">
                                {loading ? <Skeleton width={160} /> : user?.email}
                            </Typography>

                        </Box>

                        <Divider sx={{ my: 2 }} />

                        {/* Details */}
                        <Box sx={{ display: "flex", flexDirection: "column", gap: 1.5 }}>

                            <Typography>
                                <strong>Role:</strong>{" "}
                                {loading ? <Skeleton width={80} /> : user?.role}
                            </Typography>

                            <Typography>
                                <strong>User ID:</strong>{" "}
                                {loading ? <Skeleton width={120} /> : user?.userId}
                            </Typography>

                            <Typography>
                                <strong>Provider:</strong>{" "}
                                {loading ? <Skeleton width={100} /> : user?.provider}
                            </Typography>

                        </Box>

                    </CardContent>
                </Card>

            </Box>
        </DashboardLayout>
    );
}