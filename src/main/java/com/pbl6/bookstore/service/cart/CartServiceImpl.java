package com.pbl6.bookstore.service.cart;

import com.pbl6.bookstore.dto.request.CartItemRequestDTO;
import com.pbl6.bookstore.dto.response.CartDetailResponseDTO;
import com.pbl6.bookstore.dto.response.CartItemInfoResponseDTO;
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

import java.util.List;
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
        int id = Integer.parseInt(context.getAuthentication().getName());
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new AppException(ErrorCode.USER_NOT_FOUND);
        }
        Optional<Book> book = bookRepository.findById(request.getBookID());
        if (book.isPresent()) {
            if (book.get().getAvailableQuantity() >= request.getQuantity()) {
                Cart cart = user.get().getCart();
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

                book.get().setAvailableQuantity(book.get().getAvailableQuantity()
                        - request.getQuantity());

                cartItemRepository.save(cartItem);

                return CartItemQuantityResponseDTO.builder()
                        .totalDistinctItems(cartItemRepository.countByCart(cart))
                        .build();
            }
            throw new AppException(ErrorCode.QUANTITY_EXCEED);
        }
        else {
            throw new AppException(ErrorCode.BOOK_ID_NOT_FOUND);
        }

    }

    @Override
    @PreAuthorize("hasRole('user')")
    public CartDetailResponseDTO getCartDetail() {
        SecurityContext context = SecurityContextHolder.getContext();
        int id = Integer.parseInt(context.getAuthentication().getName());
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
        Cart cart = user.get().getCart();

        List<CartItem> cartItems = cartItemRepository.findByCart(cart);

        List<CartItemInfoResponseDTO> itemInfoDTOList = cartItems.stream()
                .map(item -> CartItemInfoResponseDTO.builder()
                        .id(item.getBook().getId())
                        .title(item.getBook().getTitle())
                        .soldQuantity(item.getBook().getSoldQuantity())
                        .availableQuantity(item.getBook().getAvailableQuantity())
                        .addedQuantity(item.getQuantity())
                        .originalPrice(item.getBook().getOriginalPrice())
                        .discountedPrice(item.getBook().getDiscountedPrice())
                        .imageLink(item.getBook().getImageLink())
                        .build()).toList();

        return CartDetailResponseDTO.builder()
                .totalDistinctItems(cartItems.size())
                .items(itemInfoDTOList)
                .build();
    }

    @Override
    @PreAuthorize("hasRole('user')")
    public CartItemQuantityResponseDTO updateCart(CartItemRequestDTO request) {
        SecurityContext context = SecurityContextHolder.getContext();
        int id = Integer.parseInt(context.getAuthentication().getName());
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new AppException(ErrorCode.USER_NOT_FOUND);
        }
        Cart cart = user.get().getCart();
        Optional<Book> book = bookRepository.findById(request.getBookID());
        if (book.isEmpty()) {
            throw new AppException(ErrorCode.BOOK_ID_NOT_FOUND);
        }
        CartItem cartItem = cartItemRepository.findByCartAndBook(cart, book.get());
        if (cartItem == null) {
            throw new AppException(ErrorCode.ITEM_NOT_FOUND);
        }
        if (book.get().getAvailableQuantity() + cartItem.getQuantity() < request.getQuantity()) {
            throw new AppException(ErrorCode.QUANTITY_EXCEED);
        }
        book.get().setAvailableQuantity(book.get().getAvailableQuantity()
                + cartItem.getQuantity() - request.getQuantity());
        cartItem.setQuantity(request.getQuantity());
        cartItemRepository.save(cartItem);
        return CartItemQuantityResponseDTO.builder()
                .totalDistinctItems(cartItemRepository.countByCart(cart))
                .build();
    }

}
