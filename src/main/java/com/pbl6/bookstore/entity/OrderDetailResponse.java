package com.pbl6.bookstore.entity;

import com.pbl6.bookstore.dto.response.OrderItemResponse;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderDetailResponse {
    int orderID;

    String orderStatus;

    String receiver;

    String phoneNumber;

    String address;

    String dateOrder;

    String paymentMethod;

    String paymentStatus;

    List<OrderItemResponse> items;

    BigDecimal totalPrice;
}
