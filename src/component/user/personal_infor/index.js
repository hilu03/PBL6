import React from 'react'
import "./style.scss"
import { FaUserAlt, FaLock, FaPhone, FaHome   } from "react-icons/fa";
import { MdEmail  } from "react-icons/md";
import { Link, useNavigate } from 'react-router-dom';
import { useState } from 'react';
import "./style.scss"
import { registerUser } from 'services/user/userService';
import logo from "../../../assets/users/transparent.png";


const PersonalInfor = () => {
    return (
        <div className="container mt-4">
        <div className="row infor">
          <div className="col-7 box personal-info-box" style={{ padding: "30px" }}>
          
          <h5 className="text">Thông tin cá nhân</h5>
        <form>
        <div className="mb-3">
          <label htmlFor="name" className="form-label">Họ tên:</label>
          <input type="text" className="form-control" id="name" value="Thuy Linh" readOnly />
        </div>

        <div className="mb-3">
          <label htmlFor="email" className="form-label">Email:</label>
          <input type="email" className="form-control" id="email" value="example@example.com" readOnly />
        </div>

        <div className="mb-3">
          <label htmlFor="phone" className="form-label">Số điện thoại:</label>
          <input type="tel" className="form-control" id="phone" value="0123456789" readOnly />
        </div>

        <div className="mb-3">
          <label htmlFor="address" className="form-label">Địa chỉ mặc định:</label>
          <input type="text" className="form-control" id="address" value="123 Đường ABC, Quận 1, TP.HCM" readOnly />
        </div>

        <button type="submit" className="btn btn-primary save-btn">Lưu thay đổi</button>

      </form>

        </div>
          <div className="col-4 box other-info-box">
            <h2>Thông tin khác</h2>
          
        </div>
        </div>
        </div>
      
      );
   
  }
  
  export default PersonalInfor
