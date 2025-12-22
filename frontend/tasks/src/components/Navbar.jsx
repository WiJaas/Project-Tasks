import { useNavigate } from "react-router-dom";
import { useAuth } from "../hooks/useAuth";
import "../App.css";

export default function Navbar() {
  const navigate = useNavigate();
  const { user, logout } = useAuth();

  const handleLogout = () => {
    logout();
    navigate("/login");
  };

  return (
    <nav
      className="sticky top-0 z-50 w-full border-b border-slate-200
                 bg-white/80 backdrop-blur"
    >
      <div className="mx-auto flex h-16 max-w-7xl items-center
                      justify-between px-6">
        {/* Left */}
        <div className="flex items-center gap-6">
          <span
            className="cursor-pointer text-lg font-semibold text-slate-800
                       hover:text-blue-600 transition"
            onClick={() => navigate("/projects")}
          >
            🗂 TaskManager
          </span>

          <button
            className="text-sm font-medium text-slate-600
                       hover:text-slate-900 transition"
            onClick={() => navigate("/projects")}
          >
            Projects
          </button>
        </div>

        {/* Right */}
        <div className="flex items-center gap-4">
          {user && (
            <span
              className="hidden sm:block text-sm text-slate-500"
            >
              {user.email}
            </span>
          )}

          <button
            className="rounded-lg border border-slate-300 px-4 py-1.5
                       text-sm font-medium text-slate-700
                       hover:bg-slate-100 transition"
            onClick={handleLogout}
          >
            Logout
          </button>
        </div>
      </div>
    </nav>
  );
}
