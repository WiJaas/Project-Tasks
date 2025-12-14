import { useState } from "react";
import { useAuth } from "../auth/AuthContext";

export default function Login() {
  const { login, isLoading } = useAuth();

  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState(null);

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError(null);

    try {
      await login(email, password);
    } catch {
      setError("Invalid email or password");
    }
  };

  return (
    <div
      className="min-vh-100 d-flex align-items-center justify-content-center"
      style={{
        background: "linear-gradient(135deg, #667eea 0%, #764ba2 100%)",
      }}
    >
      <div
        className="card border-0 shadow-lg"
        style={{
          width: "100%",
          maxWidth: "420px",
          borderRadius: "16px",
          backdropFilter: "blur(10px)",
        }}
      >
        <div className="card-body p-4 p-md-5">
          {/* Header */}
          <div className="text-center mb-4">
            <div
              className="mb-3 mx-auto d-flex align-items-center justify-content-center"
              style={{
                width: "56px",
                height: "56px",
                borderRadius: "14px",
                background: "linear-gradient(135deg, #667eea, #764ba2)",
                color: "white",
                fontSize: "24px",
                fontWeight: "bold",
              }}
            >
              H
            </div>
            <h3 className="fw-bold mb-1">Welcome back</h3>
            <p className="text-muted mb-0">
              Sign in to your Hahn Tasks account
            </p>
          </div>

          {/* Error */}
          {error && (
            <div className="alert alert-danger text-center py-2 small">
              {error}
            </div>
          )}

          {/* Form */}
          <form onSubmit={handleSubmit}>
            {/* Email */}
            <div className="mb-3">
              <label className="form-label small fw-semibold">Email</label>
              <div className="input-group">
                <span className="input-group-text bg-light border-end-0">
                  <i className="bi bi-envelope"></i>
                </span>
                <input
                  type="email"
                  className="form-control border-start-0"
                  placeholder="admin@test.com"
                  value={email}
                  onChange={(e) => setEmail(e.target.value)}
                  required
                  autoFocus
                />
              </div>
            </div>

            {/* Password */}
            <div className="mb-4">
              <label className="form-label small fw-semibold">Password</label>
              <div className="input-group">
                <span className="input-group-text bg-light border-end-0">
                  <i className="bi bi-lock"></i>
                </span>
                <input
                  type="password"
                  className="form-control border-start-0"
                  placeholder="••••••••"
                  value={password}
                  onChange={(e) => setPassword(e.target.value)}
                  required
                />
              </div>
            </div>

            {/* Button */}
            <button
              type="submit"
              className="btn btn-primary w-100 py-2 fw-semibold"
              disabled={isLoading}
              style={{
                borderRadius: "10px",
                background: "linear-gradient(135deg, #667eea, #764ba2)",
                border: "none",
              }}
            >
              {isLoading ? "Signing in..." : "Sign in"}
            </button>
          </form>

          {/* Footer */}
          <div className="text-center mt-4 text-muted small">
            © {new Date().getFullYear()} Hahn Tasks
          </div>
        </div>
      </div>
    </div>
  );
}
