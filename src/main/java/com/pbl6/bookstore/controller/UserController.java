package com.pbl6.bookstore.controller;

import com.pbl6.bookstore.dto.UserDTO;
import com.pbl6.bookstore.entity.User;
import com.pbl6.bookstore.dto.response.APIResponse;
import com.pbl6.bookstore.dto.response.MessageResponse;
import com.pbl6.bookstore.service.user.UserServieceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    public ResponseEntity<APIResponse> findAll() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication.getName());
        authentication.getAuthorities().forEach(System.out::println);

        return ResponseEntity.ok(new APIResponse(MessageResponse.RESOURCE_FOUND, userService.findAll()));
    }

    @PostMapping("/sign-up")
    public ResponseEntity<APIResponse> addUser(@RequestBody User user) {
        if (userService.findByEmail(user.getEmail()) == null) {
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            UserDTO userDTO = userService.save(user);
            return ResponseEntity.ok(new APIResponse(MessageResponse.SIGNUP_SUCCESS, userDTO));
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new APIResponse(MessageResponse.USER_EXISTED, null));
    }

    @PutMapping("/users")
    public UserDTO updateUser(@RequestBody User user) {
        return userService.save(user);
    }


}
