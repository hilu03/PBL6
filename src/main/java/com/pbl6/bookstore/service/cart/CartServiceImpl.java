package com.pbl6.bookstore.service.cart;

import com.pbl6.bookstore.dto.request.CartItemRequestDTO;
import com.pbl6.bookstore.dto.response.CartItemQuantityResponseDTO;
import com.pbl6.bookstore.entity.Book;
import com.pbl6.bookstore.entity.Cart;
import com.pbl6.bookstore.entity.CartItem;
import com.pbl6.bookstore.entity.User;
import com.pbl6.bookstore.exception.AppException;
import com.pbl6.bookstore.exception.ErrorCode;
import com.pbl6.bookstore.repository.BookRepository;
import com.pbl6.bookstore.repository.CartItemRepository;
import com.pbl6.bookstore.repository.CartRepository;
import com.pbl6.bookstore.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CartServiceImpl implements CartService {

    CartRepository cartRepository;

    CartItemRepository cartItemRepository;

    UserRepository userRepository;

    BookRepository bookRepository;

    @Override
    @PreAuthorize("hasRole('user')")
    public CartItemQuantityResponseDTO addToCart(CartItemRequestDTO request) {
        SecurityContext context = SecurityContextHolder.getContext();

        String email = context.getAuthentication().getName();

        User user = userRepository.findByEmail(email);

        Optional<Book> book = bookRepository.findById(request.getBookID());

        if (book.isPresent()) {
            if (book.get().getAvailableQuantity() >= request.getQuantity()) {
                Cart cart = user.getCart();
                CartItem cartItem = cartItemRepository.findByCartAndBook(cart, book.get());
                if (cartItem == null) {
                    cartItem = CartItem.builder()
                            .cart(cart)
                            .book(book.get())
                            .quantity(request.getQuantity())
                            .build();
                }
                else {
                    cartItem.setQuantity(cartItem.getQuantity() + request.getQuantity());
                }

                cartItemRepository.save(cartItem);

                return CartItemQuantityResponseDTO.builder()
                        .itemQuantity(cartItemRepository.countByCart(cart))
                        .build();
            }
            throw new AppException(ErrorCode.QUANTITY_EXCEED);
        }
        else {
            throw new AppException(ErrorCode.BOOK_ID_NOT_FOUND);
        }

    }
}
