package com.pbl6.bookstore.service.login;


import com.pbl6.bookstore.dto.UserDTO;

public interface LoginService {

    UserDTO checkLogin(String email, String password);

}
