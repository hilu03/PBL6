package com.pbl6.bookstore.service.login;


import com.nimbusds.jose.JOSEException;
import com.pbl6.bookstore.dto.UserDTO;
import com.pbl6.bookstore.dto.request.IntrospectRequest;
import com.pbl6.bookstore.dto.response.IntrospectResponse;
import com.pbl6.bookstore.dto.response.LoginResponseDTO;

import java.text.ParseException;

public interface LoginService {

    LoginResponseDTO checkLogin(String email, String password);

    IntrospectResponse introspect(IntrospectRequest request) throws JOSEException, ParseException;
}
