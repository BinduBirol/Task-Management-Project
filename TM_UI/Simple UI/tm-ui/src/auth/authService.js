import { apiClient } from "../configs/apiClient";
import {AUTH_API_BASE_URL} from "../configs/api"

export const login = (email, password) => {
    return apiClient(`${AUTH_API_BASE_URL}/login`, {
        method: "POST",
        body: JSON.stringify({ email, password })
    });
};

export const googleLogin = (idToken) => {
    return apiClient(`${AUTH_API_BASE_URL}/login/google`, {
        method: "POST",
        body: JSON.stringify({ credential: idToken })
    });
};

export const getProfile = () => {
    return apiClient(`${AUTH_API_BASE_URL}/user/this`);
};