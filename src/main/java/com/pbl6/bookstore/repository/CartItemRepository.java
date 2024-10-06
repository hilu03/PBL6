package com.pbl6.bookstore.repository;

import com.pbl6.bookstore.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Integer> {

}
