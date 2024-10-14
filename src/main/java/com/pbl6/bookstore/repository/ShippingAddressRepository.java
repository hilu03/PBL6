package com.pbl6.bookstore.repository;

import com.pbl6.bookstore.entity.ShippingAddress;
import com.pbl6.bookstore.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShippingAddressRepository extends JpaRepository<ShippingAddress, Integer> {

    ShippingAddress findByUserAndIsDefault(User user, boolean isDefault);

}
