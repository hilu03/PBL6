package com.pbl6.bookstore.controller;

import com.pbl6.bookstore.dto.UserDTO;
import com.pbl6.bookstore.entity.User;
import com.pbl6.bookstore.response.APIResponse;
import com.pbl6.bookstore.response.MessageResponse;
import com.pbl6.bookstore.service.user.UserServieceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    private final UserServieceImpl userService;

    public UserController(UserServieceImpl userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    List<User> findAll() {
        return userService.findAll();
    }

    @PostMapping("/users")
    public ResponseEntity<APIResponse> addUser(@RequestBody User user) {
        if (userService.findByEmail(user.getEmail()) == null) {
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            UserDTO userDTO = userService.save(user);
            return ResponseEntity.ok(new APIResponse(MessageResponse.SIGNUP_SUCCESS, userDTO));
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new APIResponse(MessageResponse.USER_EXISTED, null));
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
    public UserDTO updateUser(@RequestBody User user) {
        return userService.save(user);
    }


}
