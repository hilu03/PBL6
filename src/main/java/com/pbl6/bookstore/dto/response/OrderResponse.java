package com.pbl6.bookstore.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderResponse {

    int orderID;

    String orderStatus;

    String paymentMethod;

    String paymentStatus;

    List<OrderItemResponse> items;

    BigDecimal totalPrice;

}
