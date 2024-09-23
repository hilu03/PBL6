package com.pbl6.bookstore.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class BookDTO {
    private String id;

    private String title;

    private BigDecimal originalPrice;

    private BigDecimal discountedPrice;

    private String imageLink;

    private int soldQuantity;

}
