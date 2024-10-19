package com.pbl6.bookstore.service.order;

import com.pbl6.bookstore.dto.request.CreateOrderRequest;
import com.pbl6.bookstore.dto.response.OrderResponse;
import com.pbl6.bookstore.entity.OrderDetailResponse;
import com.pbl6.bookstore.entity.OrderStatus;
import com.pbl6.bookstore.entity.PaymentMethod;
import com.pbl6.bookstore.entity.ShippingAddress;

import java.util.List;

public interface OrderService {

    Object createOrder(CreateOrderRequest request) throws Exception;

    String getAddressString(ShippingAddress address);

    String getOrderPaymentStatus(int orderID);

    List<PaymentMethod> getAllPaymentMethod();

    List<OrderStatus> getAllOrderStatus();

    List<OrderResponse> getOrderByStatusID(int statusID);

    OrderDetailResponse getOrderByOrderID(int orderID);

}
