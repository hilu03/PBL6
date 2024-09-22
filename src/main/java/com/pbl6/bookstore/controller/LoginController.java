package com.pbl6.bookstore.controller;

import com.nimbusds.jose.JOSEException;
import com.pbl6.bookstore.dto.request.IntrospectRequest;
import com.pbl6.bookstore.dto.request.LoginRequestDTO;
import com.pbl6.bookstore.dto.response.APIResponse;
import com.pbl6.bookstore.dto.response.MessageResponse;
import com.pbl6.bookstore.service.login.LoginServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
public class LoginController {
    private LoginServiceImpl loginService;

    public LoginController(LoginServiceImpl loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/login")
    public ResponseEntity<APIResponse> checkLogin(@RequestBody LoginRequestDTO loginRequestDTO) {
        try {

            return ResponseEntity.ok(new APIResponse(
                    MessageResponse.LOGIN_SUCCESS,
                    loginService.checkLogin(loginRequestDTO.getEmail(), loginRequestDTO.getPassword()))
            );
        }
        catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).
                    body(new APIResponse(e.getMessage(), null));
        }

    }

    @PostMapping("/introspect")
    public ResponseEntity<APIResponse> introspect(@RequestBody IntrospectRequest request) {
        try {
            if (loginService.introspect(request).isValid()) {
                return ResponseEntity.ok(new APIResponse(MessageResponse.VALID_TOKEN,
                        loginService.introspect(request)));
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).
                    body(new APIResponse(MessageResponse.UNAUTHORIZE, null));

        } catch (JOSEException | ParseException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).
                    body(new APIResponse(MessageResponse.UNAUTHORIZE, null));
        }
    }
}
