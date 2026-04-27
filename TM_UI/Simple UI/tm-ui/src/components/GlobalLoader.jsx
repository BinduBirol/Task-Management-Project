import { LinearProgress, Box } from "@mui/material";
import { useEffect, useState } from "react";
import { uiStore } from "../store/uiStore";

export default function GlobalLoader() {
    const [loading, setLoading] = useState(false);

    useEffect(() => {
        const unsubscribe = uiStore.subscribe((state) => {
            setLoading(state.loading);
        });

        return unsubscribe;
    }, []);

    if (!loading) return null;

    return (
        <Box
            sx={{
                position: "fixed",
                top: 0,
                left: 0,
                width: "100%",
                zIndex: 9999
            }}
        >
            <LinearProgress />
        </Box>
    );
}