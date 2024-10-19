package com.pbl6.bookstore.controller;

import com.pbl6.bookstore.dto.UserDTO;
import com.pbl6.bookstore.dto.request.CreateShippingAddressRequest;
import com.pbl6.bookstore.dto.request.UpdateShippingAddressRequest;
import com.pbl6.bookstore.dto.request.UserAccountRequest;
import com.pbl6.bookstore.dto.response.APIResponse;
import com.pbl6.bookstore.dto.response.MessageResponse;
import com.pbl6.bookstore.service.user.UserServiceImpl;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {

    UserServiceImpl userService;

    @GetMapping("/users")
    public ResponseEntity<APIResponse> findAll() {
        return ResponseEntity.ok(new APIResponse(MessageResponse.RESOURCE_FOUND, userService.findAll()));
    }

    @PostMapping("/sign-up")
    public ResponseEntity<APIResponse> addUser(@RequestBody UserAccountRequest user) {

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return ResponseEntity.ok(new APIResponse(MessageResponse.SIGNUP_SUCCESS, userService.createUser(user)));
    }

    @PostMapping("/sign-up/admin")
    public ResponseEntity<APIResponse> addAdmin(@RequestBody UserAccountRequest user) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return ResponseEntity.ok(new APIResponse(MessageResponse.SIGNUP_SUCCESS, userService.createAdmin(user)));
    }

    @GetMapping("/my-info")
    public ResponseEntity<APIResponse> getMyInfo() {
        try {
            return ResponseEntity.ok(new APIResponse(MessageResponse.RESOURCE_FOUND, userService.getMyInfo()));
        }
        catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new APIResponse(MessageResponse.RESOURCE_NOT_FOUND, null));
        }
    }

    @PostMapping("/user/add-address")
    public ResponseEntity<APIResponse> addNewAddress(@RequestBody @Valid CreateShippingAddressRequest request) {
        return ResponseEntity.ok(new APIResponse(MessageResponse.SHIPPING_ADDRESS_CREATE_SUCCESS,
                userService.addNewAddress(request)));
    }

    @GetMapping("/user/address")
    public ResponseEntity<APIResponse> getAllAddress() {
        return ResponseEntity.ok(new APIResponse(MessageResponse.RESOURCE_FOUND,
                userService.getAllAddress()));
    }

    @PostMapping("/user/update-address")
    public ResponseEntity<APIResponse> updateAddress(@RequestBody @Valid UpdateShippingAddressRequest request) {
        return ResponseEntity.ok(new APIResponse(MessageResponse.SHIPPING_ADDRESS_UPDATE_SUCCESS,
                userService.updateAddress(request)));
    }

//    @PutMapping("/users")
//    public UserDTO updateUser(@RequestBody UserDTO userDTO) {
//        return userService.save(user);
//    }



}
