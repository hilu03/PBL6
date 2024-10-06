package com.pbl6.bookstore.repository;

import com.pbl6.bookstore.entity.Book;
import com.pbl6.bookstore.entity.Cart;
import com.pbl6.bookstore.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Integer> {
    CartItem findByCartAndBook(Cart cart, Book book);

    long countByCart(Cart cart);

    List<CartItem> findByCart(Cart cart);
}
