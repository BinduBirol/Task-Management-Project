import { useState } from "react";
import {
    TextField,
    Button,
    Container,
    Typography,
    Box,
    Dialog,
    DialogTitle,
    DialogContent,
    Card,
    CardContent
} from "@mui/material";

import { GoogleLogin } from "@react-oauth/google";
import { useNavigate } from "react-router-dom";
import { login, googleLogin } from "../auth/authService";
import { uiStore } from "../store/uiStore";

export default function Login() {
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");

    const [errorDialog, setErrorDialog] = useState({
        open: false,
        message: ""
    });

    const navigate = useNavigate();

    // EMAIL LOGIN
    const handleLogin = async () => {
        try {
            const data = await login(email, password);

            if (data?.token) {
                localStorage.setItem("token", data.token);
				uiStore.showToast("Login successful", "success");
                navigate("/dashboard");
            } else {
                uiStore.showToast(data?.message || "Login failed", "error");
            }

        } catch (error) {
            console.error(error);
            uiStore.showToast("Server error", "error");
        }
    };

    //GOOGLE LOGIN
    const handleGoogleSuccess = async (credentialResponse) => {
        try {
            const idToken = credentialResponse.credential;

            const data = await googleLogin(idToken);

            if (data?.token) {
                localStorage.setItem("token", data.token);
				uiStore.showToast("Login successful", "success");
                navigate("/dashboard");
            } else {
                uiStore.showToast(data?.message || "Google login failed", "error");
            }

        } catch (err) {
            console.log(err);
            uiStore.showToast("Server error", "error");
        }
    };

    return (
        <Container maxWidth="xs">
            <Box sx={{
                minHeight: "100vh",
                display: "flex",
                alignItems: "center",
                justifyContent: "center"
            }}>


                <Card
                    sx={{
                        width: "100%",
                        p: 2,
                        boxShadow: 6,
                        borderRadius: 3
                    }}
                >
                    <CardContent>

                        <Typography variant="h5" sx={{ mb: 2, textAlign: "center" }}>
                            Task Time Tracker
                        </Typography>

                        <TextField
                            fullWidth
                            label="Email"
                            margin="normal"
                            onChange={(e) => setEmail(e.target.value)}
                        />

                        <TextField
                            fullWidth
                            type="password"
                            label="Password"
                            margin="normal"
                            onChange={(e) => setPassword(e.target.value)}
                        />

                        <Button
                            fullWidth
                            variant="contained"
                            sx={{ mt: 2 }}
                            onClick={handleLogin}
                        >
                            Login
                        </Button>

                        <Typography sx={{ my: 2, textAlign: "center" }}>
                            OR
                        </Typography>

                        <Box sx={{ display: "flex", justifyContent: "center" }}>
                            <GoogleLogin
                                onSuccess={handleGoogleSuccess}
                                onError={() =>
                                    setErrorDialog({
                                        open: true,
                                        message: "Google login failed"
                                    })
                                }
                            />
                        </Box>

                    </CardContent>
                </Card>

                {/* ERROR DIALOG */}
                <Dialog
                    open={errorDialog.open}
                    onClose={() =>
                        setErrorDialog({ ...errorDialog, open: false })
                    }
                >
                    <DialogTitle>Error</DialogTitle>

                    <DialogContent>
                        <Typography>
                            {errorDialog.message}
                        </Typography>
                    </DialogContent>

                    <Box sx={{ display: "flex", justifyContent: "center", p: 2 }}>
                        <Button
                            variant="contained"
                            onClick={() =>
                                setErrorDialog({ ...errorDialog, open: false })
                            }
                        >
                            OK
                        </Button>
                    </Box>
                </Dialog>
            </Box>
        </Container>
    );
}