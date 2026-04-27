import { useState } from "react";
import { TextField, Button, Container, Typography, Box } from "@mui/material";
import { Dialog, DialogTitle, DialogContent } from "@mui/material";
import { AUTH_API_BASE_URL } from "../configs/api";
import { GoogleLogin } from "@react-oauth/google";
import { useNavigate } from "react-router-dom";


export default function Login() {
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");

    const [errorDialog, setErrorDialog] = useState({
        open: false,
        message: ""
    });
	
	const navigate = useNavigate();

    const handleLogin = async () => {
        try {
            const res = await fetch(AUTH_API_BASE_URL + "/login", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify({ email, password })
            });

            const data = await res.json();

            console.log("Response:", data);

            if (res.ok) {
                localStorage.setItem("token", data.token);
                navigate("/dashboard");
            } else {
                setErrorDialog({
                    open: true,
                    message: data.message || "Login failed"
                });
            }

        } catch (error) {
            console.error(error);
            setErrorDialog({
                open: true,
                message: "Server error"
            });
        }
    };
	
	

    //Google login
    const handleGoogleSuccess = async (credentialResponse) => {
        try {
            const idToken = credentialResponse.credential;

            const res = await fetch(`${AUTH_API_BASE_URL}/login/google`, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify({ 'credential': idToken })
            });

            const data = await res.json();

            if (res.ok) {
                localStorage.setItem("token", data.token);
                navigate("/dashboard");
            } else {
                setErrorDialog({
                    open: true,
                    message: "Google login failed"
                });
            }

        } catch (err) {
            console.log(err);
            setErrorDialog({
                open: true,
                message: "Google login error"
            });
        }
    };
    return (
        <Container maxWidth="xs">
            <Box sx={{ mt: 10, textAlign: "center" }}>
                <Typography variant="h5">
                    Task Time Tracker Login
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

                {/* OR divider */}
                <Typography sx={{ my: 2 }}>OR</Typography>

                {/* GOOGLE LOGIN */}
                <GoogleLogin
                    onSuccess={handleGoogleSuccess}
                    onError={() =>
                        setErrorDialog({
                            open: true,
                            message: "Google login failed"
                        })
                    }
                />

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