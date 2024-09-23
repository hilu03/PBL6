package com.pbl6.bookstore.service.user;

import com.pbl6.bookstore.dto.UserDTO;
import com.pbl6.bookstore.entity.User;

import java.util.List;

public interface UserService {

    List<UserDTO> findAll();

    UserDTO save(User user);

    User findByUsername(String username);

    User findByEmail(String email);

    UserDTO getMyInfo();
}
