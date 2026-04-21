import axios from "axios";
import { createContext, useContext, useEffect, useState } from "react";

// Optional: define user shape (recommended)
type User = {
  name: string;
  email: string;
  imageUrl?: string;
};

type AuthContextType = {
  user: User | null;
  setUser: (user: User | null) => void;
  loadUser: () => Promise<void>;
};

const AuthContext = createContext<AuthContextType | null>(null);

export const AuthProvider = ({ children }: { children: React.ReactNode }) => {
  const [user, setUser] = useState<User | null>(null);

  const loadUser = async () => {
    try {
      const token = localStorage.getItem("token");
	  
	  console.log("Token : "+token);

      if (!token) return;

      const res = await axios.get(
        "http://localhost:8081/auth/user/this",
        {
          headers: {
            Authorization:  token, // IMPORTANT FIX
          },
        }
      );

      setUser(res.data);
    } catch (error) {
      console.error("Failed to load user:", error);
      setUser(null);
    }
  };

  useEffect(() => {
    loadUser();
  }, []);

  return (
    <AuthContext.Provider value={{ user, setUser, loadUser }}>
      {children}
    </AuthContext.Provider>
  );
};

export const useAuth = () => {
  const context = useContext(AuthContext);

  if (!context) {
    throw new Error("useAuth must be used inside AuthProvider");
  }

  return context;
};