package com.pbl6.bookstore.repository;

import com.pbl6.bookstore.entity.Order;
import com.pbl6.bookstore.entity.OrderStatus;
import com.pbl6.bookstore.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    List<Order> findByUserAndOrderStatus(User user, OrderStatus orderStatus);

}
