package com.pbl6.bookstore.repository;

import com.pbl6.bookstore.entity.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderStatusRepository extends JpaRepository<OrderStatus, Integer> {

    OrderStatus findByName(String name);

}
