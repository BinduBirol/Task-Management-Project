export const getStoredThemeMode = () =>
    localStorage.getItem("themeMode") || "system";

export const setStoredThemeMode = (mode) =>
    localStorage.setItem("themeMode", mode);