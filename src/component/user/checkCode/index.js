import React, { useEffect } from 'react';
import { useLocation } from 'react-router-dom';
import {  processLoginGG } from "services/user/userService";
import { useNavigate } from 'react-router-dom';

const CheckCode = () => {
  const location = useLocation();
  const navigate = useNavigate();


  useEffect(() => {
    const queryParams = new URLSearchParams(location.search);
    const code = queryParams.get('code'); // Lấy giá trị của tham số 'code'
    const error = queryParams.get('error');

    if(error) {
      navigate("/login")
    }

    if (code) {
        const fetchUserInfo = async () => {
                try {
                  // Lấy mã code từ localStorage
                  console.log("Code from localStorage:", code); // Kiểm tra giá trị của code
          
                  if (code) {
                    const response = await processLoginGG(code);
                    if (response.status === 200) {
                      console.log("User Info:", response); // Kiểm tra dữ liệu trả về
                      localStorage.setItem("token", response.data.data.token)
                      localStorage.setItem("name", response.data.data.fullName)
                      // localStorage.setItem("email", response.data.data.email)
                      navigate("/")

                    }
                  } else {
                    console.log("No code found in localStorage.");
                  }
                } catch (err) {
                  // Xử lý lỗi
                  console.error("Error occurred:", err);
                }
              };
              fetchUserInfo();

    } else {
      console.log('No code found in the URL');
    }
  }, [location]);

  return null; // Không cần render gì ở đây
};

export default CheckCode;