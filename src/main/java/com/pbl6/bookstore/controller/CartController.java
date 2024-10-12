package com.pbl6.bookstore.controller;

import com.pbl6.bookstore.dto.request.ItemRequestDTO;
import com.pbl6.bookstore.dto.request.RemoveItemInCartRequest;
import com.pbl6.bookstore.dto.response.APIResponse;
import com.pbl6.bookstore.dto.response.MessageResponse;
import com.pbl6.bookstore.service.cart.CartServiceImpl;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class CartController {
    CartServiceImpl cartService;

    @PostMapping("/cart")
    ResponseEntity<APIResponse> addToCart(@RequestBody @Valid ItemRequestDTO request) {
        return ResponseEntity.ok(new APIResponse(MessageResponse.ADD_TO_CART_SUCCESS,
                cartService.addToCart(request)));
    }

    @GetMapping("/cart")
    ResponseEntity<APIResponse> viewCartDetail() {
        return ResponseEntity.ok(new APIResponse(MessageResponse.RESOURCE_FOUND,
                cartService.getCartDetail()));
    }

    @PutMapping("/cart")
    ResponseEntity<APIResponse> updateQuantity(@RequestBody @Valid ItemRequestDTO request) {
        return ResponseEntity.ok(new APIResponse(MessageResponse.UPDATE_CART_SUCCESS,
                cartService.updateCart(request)));
    }

    @DeleteMapping("/cart")
    ResponseEntity<APIResponse> removeItem(@RequestBody RemoveItemInCartRequest request) {
        return ResponseEntity.ok(new APIResponse(MessageResponse.REMOVE_ITEM_SUCCESS,
                cartService.removeItem(request)));
    }
}
