package com.pbl6.bookstore.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartItemQuantityResponseDTO {
    long itemQuantity;
}
