package com.pbl6.bookstore.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookDTO {
    String id;

    String title;

    BigDecimal originalPrice;

    BigDecimal discountedPrice;

    String imageLink;

    int soldQuantity;

    Integer availableQuantity;

}
