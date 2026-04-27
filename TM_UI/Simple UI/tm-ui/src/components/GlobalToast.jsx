import { Snackbar, Alert } from "@mui/material";
import { useEffect, useState } from "react";
import { uiStore } from "../store/uiStore";

export default function GlobalToast() {
    const [toast, setToast] = useState(null);

    useEffect(() => {
        const unsubscribe = uiStore.subscribe((state) => {
            setToast(state.toast);
        });

        return unsubscribe;
    }, []);

    return (
        <Snackbar
            open={!!toast}
            anchorOrigin={{ vertical: "bottom", horizontal: "right" }}
        >
            {toast && (
                <Alert severity={toast.type} variant="filled">
                    {toast.message}
                </Alert>
            )}
        </Snackbar>
    );
}