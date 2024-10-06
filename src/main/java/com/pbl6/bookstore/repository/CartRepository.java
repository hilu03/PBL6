package com.pbl6.bookstore.repository;

import com.pbl6.bookstore.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Integer> {
    
}
