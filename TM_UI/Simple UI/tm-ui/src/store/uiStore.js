let listeners = [];

let state = {
    loading: false,
    toast: null
};

export const uiStore = {
    getState: () => state,

    setLoading: (value) => {
        state = { ...state, loading: value };
        listeners.forEach((l) => l(state));
    },

    showToast: (message, type = "info") => {
        state = {
            ...state,
            toast: { message, type }
        };

        listeners.forEach((l) => l(state));

        // auto hide after 3s
        setTimeout(() => {
            state = { ...state, toast: null };
            listeners.forEach((l) => l(state));
        }, 3000);
    },

    subscribe: (callback) => {
        listeners.push(callback);

        return () => {
            listeners = listeners.filter((l) => l !== callback);
        };
    }
};