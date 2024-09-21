package com.pbl6.bookstore.service.user;

import com.pbl6.bookstore.dao.UserRepository;
import com.pbl6.bookstore.dto.Converter;
import com.pbl6.bookstore.dto.UserDTO;
import com.pbl6.bookstore.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServieceImpl implements UserService {

    private final UserRepository userRepository;

    private Converter<User, UserDTO> converter;

    public UserServieceImpl(UserRepository userRepository, Converter<User, UserDTO> converter) {
        this.userRepository = userRepository;
        this.converter = converter;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public UserDTO save(User user) {
        userRepository.save(user);
        return converter.mapEntityToDto(user, UserDTO.class);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }


}
