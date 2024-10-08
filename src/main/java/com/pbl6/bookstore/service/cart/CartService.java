package com.pbl6.bookstore.service.cart;

import com.pbl6.bookstore.dto.request.CartItemRequestDTO;
import com.pbl6.bookstore.dto.request.RemoveItemInCartRequest;
import com.pbl6.bookstore.dto.response.CartDetailResponseDTO;
import com.pbl6.bookstore.dto.response.CartItemQuantityResponseDTO;

public interface CartService {
    CartItemQuantityResponseDTO addToCart(CartItemRequestDTO request);

    CartDetailResponseDTO getCartDetail();

    CartItemQuantityResponseDTO updateCart(CartItemRequestDTO request);

    CartItemQuantityResponseDTO removeItem(RemoveItemInCartRequest request);
}
