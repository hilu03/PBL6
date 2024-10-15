package com.pbl6.bookstore.service.user;

import com.pbl6.bookstore.dto.request.CreateShippingAddressRequest;
import com.pbl6.bookstore.dto.request.UpdateShippingAddressRequest;
import com.pbl6.bookstore.dto.request.UserAccountRequest;
import com.pbl6.bookstore.entity.Cart;
import com.pbl6.bookstore.entity.ShippingAddress;
import com.pbl6.bookstore.exception.AppException;
import com.pbl6.bookstore.exception.ErrorCode;
import com.pbl6.bookstore.mapper.AddressMapper;
import com.pbl6.bookstore.mapper.UserMapper;
import com.pbl6.bookstore.repository.ShippingAddressRepository;
import com.pbl6.bookstore.repository.UserRepository;
import com.pbl6.bookstore.dto.UserDTO;
import com.pbl6.bookstore.entity.User;
import com.pbl6.bookstore.service.authentication.AuthenticationServiceImpl;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserServiceImpl implements UserService {

    UserRepository userRepository;

    ShippingAddressRepository addressRepository;

    UserMapper userMapper;

    AuthenticationServiceImpl authenticationService;

    AddressMapper addressMapper;

    @Override
    @PreAuthorize("hasRole('admin')")
    public List<UserDTO> findAll() {
        return userRepository.findAll().stream()
                .map(userMapper::toUserDTO)
                .toList();
    }

    @Override
    public UserDTO createUser(UserAccountRequest userAccountRequest) {
        User user = userMapper.toUser(userAccountRequest);
        user.setRole("user");
        Cart cart = Cart.builder()
                .user(user)
                .build();
        user.setCart(cart);
        userRepository.save(user);
        return userMapper.toUserDTO(user);
    }

    @Override
    public UserDTO createAdmin(UserAccountRequest userAccountRequest) {
        User user = userMapper.toUser(userAccountRequest);
        user.setRole("admin");
        userRepository.save(user);
        return userMapper.toUserDTO(user);
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

        int id = Integer.parseInt(context.getAuthentication().getName());
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new AppException(ErrorCode.USER_NOT_FOUND);
        }
        return userMapper.toUserDTO(user.get());
    }

    @Override
    @PreAuthorize("hasRole('user')")
    public List<ShippingAddress> addNewAddress(CreateShippingAddressRequest request) {
        User user = authenticationService.getUserFromToken();

        ShippingAddress address = addressMapper.toShippingAddress(request);

        if (user.getAddressList().isEmpty()) {
            address.setDefault(true);
        }
        else if (request.getIsDefault() != null) {
            if (request.getIsDefault()) {
                ShippingAddress defaultAddress = addressRepository.findByUserAndIsDefault(user, true);
                defaultAddress.setDefault(false);
                addressRepository.save(defaultAddress);
            }

            address.setDefault(request.getIsDefault());
        }

        address.setUser(user);
        user.getAddressList().add(address);

        userRepository.save(user);

        return user.getAddressList();
    }

    @Override
    @PreAuthorize("hasRole('user')")
    public List<ShippingAddress> getAllAddress() {
        User user = authenticationService.getUserFromToken();
        return user.getAddressList();
    }

    @Override
    public List<ShippingAddress> updateAddress(UpdateShippingAddressRequest request) {
        ShippingAddress address = addressRepository.findById(request.getId())
                .orElseThrow(() -> new AppException(ErrorCode.ADDRESS_NOT_FOUND));

        User user = authenticationService.getUserFromToken();

        if (user.getAddressList().size() > 1) {
            ShippingAddress defaultAddress = addressRepository.findByUserAndIsDefault(user, true);

            if (request.getId() == defaultAddress.getId() && !request.getIsDefault()) {
                throw new AppException(ErrorCode.CONFLICT_DEFAULT_ADDRESS);
            }
            else if (request.getIsDefault()) {
                defaultAddress.setDefault(false);
                addressRepository.save(defaultAddress);
            }

            address.setDefault(request.getIsDefault());

        }

        address.setReceiver(request.getReceiver());
        address.setPhoneNumber(request.getPhoneNumber());
        address.setAddress(request.getAddress());
        address.setWard(request.getWard());
        address.setDistrict(request.getDistrict());
        address.setCity(request.getCity());

        addressRepository.save(address);

        return user.getAddressList();
    }


}
