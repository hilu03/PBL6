package com.pbl6.bookstore.service.order;

import com.pbl6.bookstore.dto.request.CreateOrderRequest;
import com.pbl6.bookstore.entity.ShippingAddress;

public interface OrderService {

    Object createOrder(CreateOrderRequest request) throws Exception;

    String getAddressString(ShippingAddress address);

}
