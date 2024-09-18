package com.pbl6.bookstore.service.user;

import com.pbl6.bookstore.entity.User;

import java.util.List;

public interface UserService {

    List<User> findAll();

    User save(User user);

    User findByUsername(String username);

    User findByEmail(String email);
}
