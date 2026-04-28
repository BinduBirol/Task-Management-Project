import { uiStore } from "../store/uiStore";

export const apiClient = async (url, options = {}) => {
    try {
        uiStore.setLoading(true);

        const token = localStorage.getItem("token");

        const res = await fetch(url, {
            ...options,
            headers: {
                "Content-Type": "application/json",
                ...(token ? { Authorization: `${token}` } : {}),
                ...options.headers
            }
        });

        // 🔥 handle auth failure
        if (res.status === 401) {
            localStorage.removeItem("token");
            window.location.href = "/login";
            return;
        }

        const data = await res.json().catch(() => ({}));

        // 🔥 handle non-2xx responses properly
        if (!res.ok) {
            throw {
                status: res.status,
                ...data
            };
        }

        return data;

    } catch (error) {
        console.error("API Error:", error);
        throw error;
    } finally {
        uiStore.setLoading(false);
    }
};