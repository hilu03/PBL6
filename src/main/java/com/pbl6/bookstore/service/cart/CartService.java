package com.pbl6.bookstore.service.cart;

import com.pbl6.bookstore.dto.request.ItemRequestDTO;
import com.pbl6.bookstore.dto.request.RemoveItemInCartRequest;
import com.pbl6.bookstore.dto.response.CartDetailResponseDTO;
import com.pbl6.bookstore.dto.response.CartItemQuantityResponseDTO;

public interface CartService {
    CartItemQuantityResponseDTO addToCart(ItemRequestDTO request);

    CartDetailResponseDTO getCartDetail();

    CartItemQuantityResponseDTO updateCart(ItemRequestDTO request);

    CartItemQuantityResponseDTO removeItem(RemoveItemInCartRequest request);
}
