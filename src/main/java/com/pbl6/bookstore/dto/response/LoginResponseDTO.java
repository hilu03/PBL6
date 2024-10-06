package com.pbl6.bookstore.dto.response;


import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LoginResponseDTO {

    String token;

    String fullName;

    String role;

    String email;

    String username;

    long itemQuantityInCart;

}
