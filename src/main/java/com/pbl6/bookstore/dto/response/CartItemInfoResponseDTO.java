package com.pbl6.bookstore.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartItemInfoResponseDTO {
    String id;

    String title;

    BigDecimal originalPrice;

    BigDecimal discountedPrice;

    String imageLink;

    int soldQuantity;

    Integer availableQuantity;

    int addedQuantity;

}
