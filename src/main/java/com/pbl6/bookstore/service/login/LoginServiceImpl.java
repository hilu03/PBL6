package com.pbl6.bookstore.service.login;

import com.pbl6.bookstore.dao.UserRepository;
import com.pbl6.bookstore.dto.Converter;
import com.pbl6.bookstore.dto.UserDTO;
import com.pbl6.bookstore.entity.User;
import com.pbl6.bookstore.response.MessageResponse;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {

    private UserRepository userRepository;

    private Converter<User, UserDTO> converter;

    public LoginServiceImpl(UserRepository userRepository, Converter<User, UserDTO> converter) {
        this.userRepository = userRepository;
        this.converter = converter;
    }

    @Override
    public UserDTO checkLogin(String email, String password) {
        // -1: not exist
        // 0: wrong password
        // 1: login successfully

        User user = userRepository.findByEmail(email);

        if (user != null) {
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

            if (passwordEncoder.matches(password, user.getPassword())) return converter.mapEntityToDto(user, UserDTO.class);

            throw new RuntimeException(MessageResponse.LOGIN_FAIL);

        }

        throw new RuntimeException(MessageResponse.USER_NOT_FOUND);
    }
}
