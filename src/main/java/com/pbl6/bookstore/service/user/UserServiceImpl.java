package com.pbl6.bookstore.service.user;

import com.pbl6.bookstore.dto.request.UserAccountRequest;
import com.pbl6.bookstore.entity.Cart;
import com.pbl6.bookstore.repository.UserRepository;
import com.pbl6.bookstore.dto.Converter;
import com.pbl6.bookstore.dto.UserDTO;
import com.pbl6.bookstore.dto.response.MessageResponse;
import com.pbl6.bookstore.entity.User;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserServiceImpl implements UserService {

    UserRepository userRepository;

    Converter<User, UserDTO> userDTOConverter;
    Converter<User, UserAccountRequest> userAccountRequestConverter;

    @Override
    @PreAuthorize("hasRole('admin')")
    public List<UserDTO> findAll() {
        return userRepository.findAll().stream()
                .map(user -> userDTOConverter.mapEntityToDto(user, UserDTO.class))
                .toList();
    }

    @Override
    public UserDTO createUser(UserAccountRequest userAccountRequest) {
        User user = userAccountRequestConverter.mapDtoToEntity(userAccountRequest, User.class);
        user.setRole("user");
        Cart cart = Cart.builder()
                .user(user)
                .build();
        user.setCart(cart);
        userRepository.save(user);
        return userDTOConverter.mapEntityToDto(user, UserDTO.class);
    }

    @Override
    public UserDTO createAdmin(UserAccountRequest userAccountRequest) {
        User user = userAccountRequestConverter.mapDtoToEntity(userAccountRequest, User.class);
        user.setRole("admin");
        userRepository.save(user);
        return userDTOConverter.mapEntityToDto(user, UserDTO.class);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public UserDTO getMyInfo() {
        SecurityContext context = SecurityContextHolder.getContext();

        String email = context.getAuthentication().getName();

        User user = userRepository.findByEmail(email);

        if (user != null) {
            return userDTOConverter.mapEntityToDto(user, UserDTO.class);
        }

        throw new RuntimeException(MessageResponse.USER_NOT_FOUND);

    }


}
