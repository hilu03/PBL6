package com.pbl6.bookstore.service.cart;

import com.pbl6.bookstore.dto.request.ItemRequestDTO;
import com.pbl6.bookstore.dto.request.RemoveItemInCartRequest;
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
import com.pbl6.bookstore.repository.UserRepository;
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
public class CartServiceImpl implements CartService {

    CartItemRepository cartItemRepository;

    AuthenticationServiceImpl authenticationService;

    BookRepository bookRepository;


    @Override
    @PreAuthorize("hasRole('user')")
    public CartItemQuantityResponseDTO addToCart(ItemRequestDTO request) {
        User user = authenticationService.getUserFromToken();
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
        User user = authenticationService.getUserFromToken();;
        Cart cart = user.getCart();

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
    public CartItemQuantityResponseDTO updateCart(ItemRequestDTO request) {
        User user = authenticationService.getUserFromToken();;
        Cart cart = user.getCart();
        Optional<Book> book = bookRepository.findById(request.getBookID());
        if (book.isEmpty()) {
            throw new AppException(ErrorCode.BOOK_ID_NOT_FOUND);
        }
        CartItem cartItem = cartItemRepository.findByCartAndBook(cart, book.get());
        if (cartItem == null) {
            throw new AppException(ErrorCode.ITEM_NOT_FOUND);
        }
        if (book.get().getAvailableQuantity() < request.getQuantity()) {
            throw new AppException(ErrorCode.QUANTITY_EXCEED);
        }
        cartItem.setQuantity(request.getQuantity());
        cartItemRepository.save(cartItem);
        return CartItemQuantityResponseDTO.builder()
                .totalDistinctItems(cartItemRepository.countByCart(cart))
                .build();
    }

    @Override
    @PreAuthorize("hasRole('user')")
    public CartItemQuantityResponseDTO removeItem(RemoveItemInCartRequest request) {
        User user = authenticationService.getUserFromToken();;
        Cart cart = user.getCart();
        for (String bookID: request.getBookIDs()) {
            Optional<Book> book = bookRepository.findById(bookID);
            if (book.isEmpty()) {
                throw new AppException(ErrorCode.BOOK_ID_NOT_FOUND);
            }
            CartItem cartItem = cartItemRepository.findByCartAndBook(cart, book.get());
            if (cartItem == null) {
                throw new AppException(ErrorCode.ITEM_NOT_FOUND);
            }
            cartItemRepository.delete(cartItem);
        }
        return CartItemQuantityResponseDTO.builder()
                .totalDistinctItems(cartItemRepository.countByCart(cart))
                .build();
    }

}
