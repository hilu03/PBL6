import React from 'react';
import './style.scss';
import { FaUserAlt, FaLock } from "react-icons/fa";
import { Link, useNavigate  } from 'react-router-dom'; 
import { useState } from 'react';
import { processLogin } from 'services/user/userService';


const LoginForm = () => {
  const [email, setUsername] = useState(''); 
  const [password, setPassword] = useState(''); 
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


  return (
    <div className="heli">
      <div className="container">
      <div className="a">
    <div className='wrapper'>
    <form onSubmit={handleSubmit}> {/* Gọi hàm handleSubmit khi form submit */}
        <h1>Login</h1>

        <div className="input-box">
          <input type="email" placeholder="Email" 
          value={email} 
          onChange={(e) => setUsername(e.target.value)} required /> {/* Cập nhật state khi người dùng nhập */}
          <FaUserAlt className='icon' />
        </div>

        <div className="input-box">
          <input type="password" 
                 placeholder="Password" 
                 value={password}
                 onChange={(e) => setPassword(e.target.value)}
                 required />
          <FaLock className='icon' />
        </div>

        <div className="remember-forgot">
          <label><input type="checkbox" /> Remember me</label>
          <a href="#">Forgot password</a>
        </div>
        <button type="submit">Login</button>
        <div className="register-link">
          <p>Don't have an account? <Link to="/register">Register</Link></p> {/* Link to register */}
        </div>
      </form>
    </div>
    </div>
    </div>
    </div>
  );
};

export default LoginForm;
