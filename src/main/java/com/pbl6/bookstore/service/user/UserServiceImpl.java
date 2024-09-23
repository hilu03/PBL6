package com.pbl6.bookstore.service.user;

import com.pbl6.bookstore.dao.UserRepository;
import com.pbl6.bookstore.dto.Converter;
import com.pbl6.bookstore.dto.UserDTO;
import com.pbl6.bookstore.entity.User;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserServiceImpl implements UserService {

    UserRepository userRepository;

    Converter<User, UserDTO> converter;


    @Override
    public List<UserDTO> findAll() {
        return userRepository.findAll().stream().map(user -> converter.mapEntityToDto(user, UserDTO.class)).toList();
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
