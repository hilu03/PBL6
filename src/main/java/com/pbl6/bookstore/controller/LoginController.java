package com.pbl6.bookstore.controller;

import com.pbl6.bookstore.dto.LoginDTO;
import com.pbl6.bookstore.dto.UserDTO;
import com.pbl6.bookstore.response.APIResponse;
import com.pbl6.bookstore.response.MessageResponse;
import com.pbl6.bookstore.service.login.LoginServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    private LoginServiceImpl loginService;

    public LoginController(LoginServiceImpl loginService) {
        this.loginService = loginService;
    }

    @GetMapping("/login")
    public ResponseEntity<APIResponse> checkLogin(@RequestBody LoginDTO loginDTO) {
        try {
            UserDTO userDTO = loginService.checkLogin(loginDTO.getEmail(), loginDTO.getPassword());
            return ResponseEntity.ok(new APIResponse(MessageResponse.LOGIN_SUCCESS, userDTO));
        }
        catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new APIResponse(e.getMessage(), null));
        }

    }
}
