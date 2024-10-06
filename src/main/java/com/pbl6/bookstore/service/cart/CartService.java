package com.pbl6.bookstore.service.cart;

import com.pbl6.bookstore.dto.request.CartItemRequestDTO;

public interface CartService {
    Object addToCart(CartItemRequestDTO request);
}
