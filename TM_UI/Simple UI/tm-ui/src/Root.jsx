import { useMemo } from "react";
import { ThemeProvider } from "@mui/material/styles";
import CssBaseline from "@mui/material/CssBaseline";
import { useMediaQuery } from "@mui/material";

import App from "./App.jsx";
import { getTheme } from "./theme/theme";
import { getStoredThemeMode } from "./configs/themeMode.js";
import { GoogleOAuthProvider } from "@react-oauth/google";
import { GOOGLE_CLIENT_ID } from "./configs/auth.js";
import GlobalToast from "./components/GlobalToast";
import GlobalLoader from "./components/GlobalLoader";

export default function Root() {


    const prefersDarkMode = useMediaQuery(
        "(prefers-color-scheme: dark)"
    );

    const mode = getStoredThemeMode();

    const resolvedMode =
        mode === "system"
            ? prefersDarkMode ? "dark" : "light"
            : mode;

    const theme = useMemo(
        () => getTheme(resolvedMode),
        [resolvedMode]
    );


    return (
        <GoogleOAuthProvider clientId={GOOGLE_CLIENT_ID}>
            <ThemeProvider theme={theme}>
                <CssBaseline />
                <GlobalLoader />
                <GlobalToast />
                <App />
            </ThemeProvider>
        </GoogleOAuthProvider>
    );
}