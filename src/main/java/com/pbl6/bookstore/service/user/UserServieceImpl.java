package com.pbl6.bookstore.service.user;

import com.pbl6.bookstore.dao.UserRepository;
import com.pbl6.bookstore.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServieceImpl implements UserService {

    private UserRepository userRepository;

    public UserServieceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User save(User user) {
        userRepository.save(user);
        return user;
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
