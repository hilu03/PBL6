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
    public User findUserByEmail(@RequestParam(required = false) String email,
                                @RequestParam(required = false) String username) {
        if (email != null) {
            return userService.findByEmail(email);
        } else if (username != null) {
            return userService.findByUsername(username);
        }
        // xử lý trường hợp không có tham số
         return null;
    }

    @PutMapping("/users")
    public User updateUser(@RequestBody User user) {
        return userService.save(user);
    }


}
