package com.pbl6.bookstore.service.login;

import com.pbl6.bookstore.dao.UserRepository;
import com.pbl6.bookstore.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {

    private UserRepository userRepository;

    @Autowired
    public LoginServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public String checkLogin(String username, String password) {
        User user = userRepository.findByUsername(username);
        // temporary solution
        if (user != null && user.getPassword().equals(password)) {
            return user.getUsername();
        }
        return null;
    }
}
