package com.pbl6.bookstore.controller;

import com.pbl6.bookstore.dto.request.CartItemRequestDTO;
import com.pbl6.bookstore.dto.response.APIResponse;
import com.pbl6.bookstore.dto.response.MessageResponse;
import com.pbl6.bookstore.service.cart.CartServiceImpl;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class CartController {
    CartServiceImpl cartService;

    @PostMapping("/cart/add")
    ResponseEntity<APIResponse> addToCart(@RequestBody @Valid CartItemRequestDTO request) {
        return ResponseEntity.ok(new APIResponse(MessageResponse.ADD_TO_CART_SUCCESS,
                cartService.addToCart(request)));
    }

}
