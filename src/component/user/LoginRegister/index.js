import React, { useEffect } from "react";
import "./stylee.scss";
import { FaUserAlt, FaLock, FaGoogle, FaHome} from "react-icons/fa";
import { Link, useNavigate} from "react-router-dom";
import { useState } from "react";
import { processLogin, processLoginGG } from "services/user/userService";
import { OAuthConfig } from "Configurations/configGG";

const LoginForm = () => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState(null);
  const [userInfors, setUserInfors] = useState([]);
  const navigate = useNavigate();


  const handleGoogleLogin = () => {
    const callbackUrl = OAuthConfig.redirectUri;
    const authUrl = OAuthConfig.authUri;
    const googleClientId = OAuthConfig.clientId;

    const targetUrl = `${authUrl}?redirect_uri=${encodeURIComponent(
      callbackUrl
    )}&response_type=code&client_id=${googleClientId}&scope=openid%20email%20profile`;

    console.log(targetUrl);

    window.location.href = targetUrl;
  };


  const handleSubmit = async (e) => {
    e.preventDefault(); //
    try {
      const response = await processLogin(email, password);
      if (response.status === 200) {
        const userID = response.data.data.id
        const name = response.data.data.fullName
        const token = response.data.data.token
        localStorage.setItem("name", name)
        localStorage.setItem("token", token)
        navigate('/');
      }
      // else if (response.status === 401){
      //   setError("Fail to login: Incorrect email or password.");
      // }
    } catch (err) {
      setError(err?.response?.data?.message || 'Đăng nhập thất bại');
    }
  };


  return (
    <div className="helo">
      <div className="login-form">
        <form onSubmit={handleSubmit}>
          <h1>Đăng nhập</h1>

          {error && <div className="error-message" style={{ color: "red" }}>{error}</div>}

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
              placeholder="Mật khẩu"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              required
            />
            <FaLock className="icon" />
          </div>

          <div className="remember-forgot">
            <label>
              <input type="checkbox" /> Ghi nhớ đăng nhập
            </label>
            <a href="#">Quên mật khẩu</a>
          </div>

          <button className="login" type="submit">Đăng nhập</button>

          <button className="google-login" onClick={handleGoogleLogin}>
            <FaGoogle className="icon" /> Đăng nhập với Google
          </button>
          <div className="register-link">
            <p>
              Bạn chưa có tài khoản? <Link to="/register">Đăng kí</Link>
            </p>
          </div>

          <div className="homepage">
          <Link to="/">
          <FaHome /> 
        </Link>
          </div>
          
        </form>
      </div>
    </div>
  );
};

export default LoginForm;
