package com.pbl6.bookstore.service.login;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.pbl6.bookstore.dao.UserRepository;
import com.pbl6.bookstore.dto.Converter;
import com.pbl6.bookstore.dto.request.IntrospectRequest;
import com.pbl6.bookstore.dto.response.IntrospectResponse;
import com.pbl6.bookstore.dto.response.LoginResponseDTO;
import com.pbl6.bookstore.entity.User;
import com.pbl6.bookstore.dto.response.MessageResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LoginServiceImpl implements LoginService {

    final UserRepository userRepository;

    final Converter<User, LoginResponseDTO> converter;

    @Value("${jwt.signerKey}")
    String SIGNER_KEY;

    @Override
    public LoginResponseDTO checkLogin(String email, String password) {
        // -1: not exist
        // 0: wrong password
        // 1: login successfully

        User user = userRepository.findByEmail(email);

        if (user != null) {
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

            if (passwordEncoder.matches(password, user.getPassword())) {

                String token = generateToken(user);

                LoginResponseDTO loginResponseDTO = converter.mapEntityToDto(user, LoginResponseDTO.class);

                loginResponseDTO.setToken(token);

                return loginResponseDTO;
            }

            throw new RuntimeException(MessageResponse.LOGIN_FAIL);

        }

        throw new RuntimeException(MessageResponse.USER_NOT_FOUND);
    }

    @Override
    public IntrospectResponse introspect(IntrospectRequest request) throws JOSEException, ParseException {

            JWSVerifier jwsVerifier = new MACVerifier(SIGNER_KEY.getBytes());

            SignedJWT signedJWT = SignedJWT.parse(request.getToken());

            Date expireTime = signedJWT.getJWTClaimsSet().getExpirationTime();

            return new IntrospectResponse(expireTime.after(new Date()) && signedJWT.verify(jwsVerifier));


    }

    private String generateToken(User user) {
        JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getEmail())
                .issuer("bookstore.pbl6.com")
                .issueTime(new Date())
                .expirationTime(new Date(Instant.now()
                        .plus(1, ChronoUnit.HOURS).toEpochMilli()))
                .claim("scope", user.getRole())
                .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(jwsHeader, payload);

        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }
    }


}
