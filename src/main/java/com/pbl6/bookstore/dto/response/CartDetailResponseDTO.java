package com.pbl6.bookstore.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartDetailResponseDTO {
    long totalDistinctItems;

    List<CartItemInfoResponseDTO> items;
}
