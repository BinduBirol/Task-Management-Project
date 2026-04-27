import { uiStore } from "../store/uiStore";

export const apiClient = async (url, options = {}) => {
    try {
        uiStore.setLoading(true);

        const token = localStorage.getItem("token");

        const res = await fetch(url, {
            ...options,
            headers: {
                "Content-Type": "application/json",
                ...(token ? { Authorization: `Bearer ${token}` } : {}),
                ...options.headers
            }
        });

        if (res.status === 401) {
            localStorage.removeItem("token");
            window.location.href = "/login";
            return;
        }

        return await res.json();

    } finally {
        uiStore.setLoading(false);
    }
};