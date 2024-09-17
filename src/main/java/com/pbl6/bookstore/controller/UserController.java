package com.pbl6.bookstore.controller;

import com.pbl6.bookstore.entity.User;
import com.pbl6.bookstore.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    List<User> findAll() {
        return userService.findAll();
    }

    @PostMapping("/users")
    public User addUser(@RequestBody User user) {
        return userService.save(user);
    }

    @GetMapping("/user")
    public User findUserByUsername(@RequestParam(value = "username", required = true) String username) {
        return userService.findByUsername(username);
    }

    @GetMapping("/user")
    public User findUserByEmail(@RequestParam(value = "email", required = true) String email) {
        return userService.findByEmail(email);
    }

    @PutMapping("/users")
    public User updateUser(@RequestBody User user) {
        return userService.save(user);
    }


}
