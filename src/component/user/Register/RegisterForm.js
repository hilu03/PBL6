import React from 'react'
import { FaUserAlt, FaLock   } from "react-icons/fa";
import { MdEmail  } from "react-icons/md";
import { Link, useNavigate } from 'react-router-dom';
import { useState } from 'react';
import "./style.scss"
import { registerUser } from 'services/user/userService';


const RegisterForm = () => {
  const [fullName, setFullName] = useState('');
  const [email, setEmail] = useState('');
  const [phone, setPhone] = useState('');
  const [password, setPassword] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('');
  const [error, setError] = useState(null);
  const navigate = useNavigate();


  const handleSubmit = async (e) => {
    e.preventDefault();

    if (password !== confirmPassword) {
      setError('Mật khẩu không khớp!');
      return;
    }

    try {
      const response = await registerUser(fullName, email, phone, password);
      if (response.status === 200) { // Giả sử API trả về status 201 khi đăng ký thành công
        navigate('/'); // Điều hướng về trang đăng nhập
      }
    } catch (err) {
      setError(err.response?.data?.message || 'Đăng ký thất bại');
    }
  };





  return (
    <div className="heli">
      <div className="container">
      <div className="a">

      <div className='wrapperRegister'>
      <form onSubmit={handleSubmit}>
        <h1>Đăng kí</h1>
        <div className="input-box">
          <input type="text" placeholder="Họ tên" 
            value={fullName} 
            onChange={(e) => setFullName(e.target.value)} 
            required />
          <FaUserAlt className='icon' />
        </div>

        <div className="input-box">
          <input type="email" placeholder="Email" 
            value={email} 
            onChange={(e) => setEmail(e.target.value)} 
            required />
          <MdEmail className='icon' />
        </div>

        <div className="input-box">
          <input type="text" placeholder="Số điện thoại" 
                      value={phone} 
                      onChange={(e) => setPhone(e.target.value)} 
                      required />
          <FaLock className='icon' />
        </div>

        <div className="input-box">
          <input type="password" placeholder="Mật khẩu" 
          value={password} 
          onChange={(e) => setPassword(e.target.value)}           
          required />
          <FaLock className='icon' />
        </div>

        <div className="input-box">
          <input type="password" placeholder="Nhập lại mật khẩu"    
          value={confirmPassword}   
          onChange={(e) => setConfirmPassword(e.target.value)} 
          
          required />
          <FaLock className='icon' />
        </div>

        <div className="remember-forgot">
          <label><input type="checkbox" /> Tôi đồng ý với các điều khoản </label>
        </div>
        <button type="submit">Đăng kí</button>
        <div className="register-link">
          <p>Bạn đã có tài khoản? <Link to="/login">Đăng nhập</Link></p> {/* Link to register */}
        </div>
      </form>
    </div>
      </div>
      </div>
      </div>
        
  )
}

export default RegisterForm