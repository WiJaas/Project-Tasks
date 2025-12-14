import Login from "./pages/Login";
import ProtectedRoute from "./auth/ProtectedRoute";

function App() {
  return (
    <Login />
    // later:
    // <ProtectedRoute><Dashboard /></ProtectedRoute>
  );
}

export default App;
