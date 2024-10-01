import React from "react";
import "./stylee.scss";
import { FaUserAlt, FaLock, FaGoogle} from "react-icons/fa";
import { Link, useNavigate} from "react-router-dom";
import { useState } from "react";
import { processLogin } from "services/user/userService";

const LoginForm = () => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState(null);
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault(); //
    try {
      const response = await processLogin(email, password);
      if (response.status === 200) {
        const userID = response.data.data.id
        const name = response.data.data.fullName
        localStorage.setItem("userID", userID)
        localStorage.setItem("name", name)
        navigate('/');
      }
    } catch (err) {
      setError(err?.response?.data?.message || 'Đăng nhập thất bại');
    }
  };

  const handleGoogleLogin = () => {
    
  };

  
  return (
    <div className="helo">
      <div className="login-form">
        <form onSubmit={handleSubmit}>
          <h1>Login</h1>

          <div className="input-box">
            <input
              type="email"
              placeholder="Email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              required
            />
            <FaUserAlt className="icon" />
          </div>

          <div className="input-box">
            <input
              type="password"
              placeholder="Password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              required
            />
            <FaLock className="icon" />
          </div>

          <div className="remember-forgot">
            <label>
              <input type="checkbox" /> Remember me
            </label>
            <a href="#">Forgot password</a>
          </div>

          <button className="login" type="submit">Login</button>

          <button className="google-login" onClick={handleGoogleLogin}>
            <FaGoogle className="icon" /> Continue with Google
          </button>
          <div className="register-link">
            <p>
              Don't have an account? <Link to="/register">Register</Link>
            </p>
          </div>
        </form>
      </div>
    </div>
  );
};

export default LoginForm;
