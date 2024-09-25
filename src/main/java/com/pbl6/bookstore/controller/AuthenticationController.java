package com.pbl6.bookstore.controller;

import com.nimbusds.jose.JOSEException;
import com.pbl6.bookstore.dto.request.LoginRequestDTO;
import com.pbl6.bookstore.dto.request.LogoutRequestDTO;
import com.pbl6.bookstore.dto.response.APIResponse;
import com.pbl6.bookstore.dto.response.MessageResponse;
import com.pbl6.bookstore.service.authentication.AuthenticationServiceImpl;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {
    AuthenticationServiceImpl authenticationService;

    @PostMapping("/login")
    public ResponseEntity<APIResponse> checkLogin(@RequestBody LoginRequestDTO loginRequestDTO) {
        try {

            return ResponseEntity.ok(new APIResponse(
                    MessageResponse.LOGIN_SUCCESS,
                    authenticationService.checkLogin(loginRequestDTO.getEmail(), loginRequestDTO.getPassword()))
            );
        }
        catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).
                    body(new APIResponse(e.getMessage(), null));
        }

    }

    @PostMapping("/log-out")
    public ResponseEntity<APIResponse> logout(@RequestBody LogoutRequestDTO logoutRequestDTO)
            throws ParseException, JOSEException {
        authenticationService.logout(logoutRequestDTO);
        return ResponseEntity.ok(new APIResponse(
                MessageResponse.LOGOUT_SUCCESS,
                null));
    }
}
