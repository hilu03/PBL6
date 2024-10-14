package com.pbl6.bookstore.service.user;

import com.pbl6.bookstore.dto.UserDTO;
import com.pbl6.bookstore.dto.request.ShippingAddressRequest;
import com.pbl6.bookstore.dto.request.UserAccountRequest;
import com.pbl6.bookstore.entity.ShippingAddress;
import com.pbl6.bookstore.entity.User;

import java.util.List;

public interface UserService {

    List<UserDTO> findAll();

    UserDTO createUser(UserAccountRequest userAccountRequest);

    UserDTO createAdmin(UserAccountRequest userAccountRequest);

    User findByUsername(String username);

    User findByEmail(String email);

    UserDTO getMyInfo();

    List<ShippingAddress> addNewAddress(ShippingAddressRequest request);

    List<ShippingAddress> getAllAddress();
}
