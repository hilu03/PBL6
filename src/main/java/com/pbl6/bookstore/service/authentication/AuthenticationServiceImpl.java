package com.pbl6.bookstore.service.authentication;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.pbl6.bookstore.dto.request.ExchangeTokenRequestDTO;
import com.pbl6.bookstore.dto.request.LogoutRequestDTO;
import com.pbl6.bookstore.dto.request.RefreshRequestDTO;
import com.pbl6.bookstore.entity.InvalidatedToken;
import com.pbl6.bookstore.exception.AppException;
import com.pbl6.bookstore.exception.ErrorCode;
import com.pbl6.bookstore.repository.InvalidatedTokenRepository;
import com.pbl6.bookstore.repository.UserRepository;
import com.pbl6.bookstore.dto.Converter;
import com.pbl6.bookstore.dto.response.IntrospectResponse;
import com.pbl6.bookstore.dto.response.LoginResponseDTO;
import com.pbl6.bookstore.entity.User;
import com.pbl6.bookstore.dto.response.MessageResponse;
import com.pbl6.bookstore.repository.fiegnclient.GoogleAuthClient;
import com.pbl6.bookstore.repository.fiegnclient.GoogleUserInfoClient;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationServiceImpl implements AuthenticationService {

    UserRepository userRepository;

    Converter<User, LoginResponseDTO> converter;

    InvalidatedTokenRepository invalidatedTokenRepository;

    GoogleAuthClient googleAuthClient;

    GoogleUserInfoClient googleUserInfoClient;

    @NonFinal
    @Value("${jwt.signerKey}")
    String SIGNER_KEY;

    @NonFinal
    @Value("${jwt.token-duration}")
    long TOKEN_DURATION;

    @NonFinal
    @Value("${jwt.refreshable-duration}")
    long REFRESHABLE_DURATION;

    @NonFinal
    @Value("${google.auth.web.client-id}")
    String WEB_CLIENT_ID;

    @NonFinal
    @Value("${google.auth.web.client-secret}")
    String WEB_CLIENT_SECRET;

    @NonFinal
    @Value("${google.auth.web.redirect-uri}")
    String WEB_REDIRECT_URI;

    @NonFinal
    String WEB_GRANT_TYPE = "authorization_code";

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
    public void logout(LogoutRequestDTO logoutRequestDTO) throws ParseException, JOSEException {
        // use try catch to make logout always return code 200 to client
        try {
            SignedJWT signedJWT = verifyToken(logoutRequestDTO.getToken(), true); // true because when logout token can't be refresh later
            String jwtID = signedJWT.getJWTClaimsSet().getJWTID();
            Date expirationTime = signedJWT.getJWTClaimsSet().getExpirationTime();
            InvalidatedToken invalidatedToken = InvalidatedToken.builder()
                    .id(jwtID)
                    .expirationTime(expirationTime)
                    .build();
            invalidatedTokenRepository.save(invalidatedToken);
        }
        catch (AppException exception) {
            log.error("Token already expired!");
        }

    }

    private SignedJWT verifyToken(String token, boolean isRefresh) throws JOSEException, ParseException {
        JWSVerifier jwsVerifier = new MACVerifier(SIGNER_KEY.getBytes());

        SignedJWT signedJWT = SignedJWT.parse(token);

        Date expireTime = (isRefresh)
                ? new Date(signedJWT.getJWTClaimsSet().getIssueTime()
                    .toInstant().plus(REFRESHABLE_DURATION, ChronoUnit.HOURS)
                    .toEpochMilli())
                : signedJWT.getJWTClaimsSet().getExpirationTime();

        boolean verified = signedJWT.verify(jwsVerifier);

        if (!(expireTime.after(new Date()) && verified)) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        if (invalidatedTokenRepository.existsById(signedJWT.getJWTClaimsSet().getJWTID())) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        return signedJWT;
    }

    private String generateToken(User user) {
        JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getEmail())
                .issuer("bookstore.pbl6.com")
                .issueTime(new Date())
                .expirationTime(new Date(Instant.now()
                        .plus(TOKEN_DURATION, ChronoUnit.HOURS).toEpochMilli()))
                .jwtID(UUID.randomUUID().toString())
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


    @Override
    public IntrospectResponse introspect(String token) throws JOSEException, ParseException {

        verifyToken(token, false);

        return new IntrospectResponse(true);
    }

    @Override
    public RefreshRequestDTO refreshToken(RefreshRequestDTO request)
            throws ParseException, JOSEException {
        SignedJWT signedJWT = verifyToken(request.getToken(), true);

        // invalidate old token
        String jwtID = signedJWT.getJWTClaimsSet().getJWTID();
        Date expirationTime = signedJWT.getJWTClaimsSet().getExpirationTime();
        InvalidatedToken invalidatedToken = InvalidatedToken.builder()
                .id(jwtID)
                .expirationTime(expirationTime)
                .build();
        invalidatedTokenRepository.save(invalidatedToken);

        User user = userRepository.findByEmail(signedJWT.getJWTClaimsSet().getSubject());

        String token = generateToken(user);

        return RefreshRequestDTO.builder()
                .token(token)
                .build();
    }

    @Override
    public LoginResponseDTO loginWithGoogle(String code) {
        var response = googleAuthClient.exchangeToken(ExchangeTokenRequestDTO.builder()
                        .code(code)
                        .clientId(WEB_CLIENT_ID)
                        .clientSecret(WEB_CLIENT_SECRET)
                        .redirectUri(WEB_REDIRECT_URI)
                        .grantType(WEB_GRANT_TYPE)
                .build());

        var userInfo = googleUserInfoClient.getUserInfo("json", response.getAccessToken());

        User user = userRepository.findByEmail(userInfo.getEmail());

        if (user == null) {
            user = User.builder()
                    .email(userInfo.getEmail())
                    .fullName(userInfo.getName())
                    .role("user")
                    .build();
            userRepository.save(user);
        }

        String token = generateToken(user);

        return LoginResponseDTO.builder()
                .token(token)
                .email(user.getEmail())
                .fullName(user.getFullName())
                .role(user.getRole())
                .build();
    }


}
