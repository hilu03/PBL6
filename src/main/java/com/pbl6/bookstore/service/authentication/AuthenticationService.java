package com.pbl6.bookstore.service.authentication;


import com.nimbusds.jose.JOSEException;
import com.pbl6.bookstore.dto.request.LogoutRequestDTO;
import com.pbl6.bookstore.dto.request.RefreshRequestDTO;
import com.pbl6.bookstore.dto.response.IntrospectResponse;
import com.pbl6.bookstore.dto.response.LoginResponseDTO;
import com.pbl6.bookstore.entity.User;

import java.text.ParseException;

public interface AuthenticationService {

    String generateToken(User user);

    LoginResponseDTO checkLogin(String email, String password);

    void logout(LogoutRequestDTO logoutRequestDTO) throws ParseException, JOSEException;

    IntrospectResponse introspect(String token) throws JOSEException, ParseException;

    RefreshRequestDTO refreshToken(RefreshRequestDTO request) throws ParseException, JOSEException;

    LoginResponseDTO loginWithGoogleByWeb(String code);

    LoginResponseDTO loginWithGoogleByApp(String googleToken);

    boolean isGoogleTokenValid(String token);

    User getUserFromToken();
}
