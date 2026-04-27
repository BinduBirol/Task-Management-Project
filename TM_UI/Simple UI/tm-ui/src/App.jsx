import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";
import Dashboard from "./pages/Dashboard";
import Login from "./auth/Login";
import ProtectedRoute from "./auth/ProtectedRoute";

export default function App() {
    return (
        <BrowserRouter>
            <Routes>

                {/* ROOT redirect */}
                <Route
                    path="/"
                    element={
                        localStorage.getItem("token")
                            ? <Navigate to="/dashboard" replace />
                            : <Navigate to="/login" replace />
                    }
                />

                {/* PUBLIC */}
                <Route path="/login" element={<Login />} />

                {/* PROTECTED */}
                <Route element={<ProtectedRoute />}>
                    <Route path="/dashboard" element={<Dashboard />} />
                </Route>

            </Routes>
        </BrowserRouter>
    );
}