package com.pbl6.bookstore.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderItemResponse {

    String bookID;

    String title;

    String imageLink;

    int quantity;

    BigDecimal originalPrice;

    BigDecimal discountedPrice;

}
