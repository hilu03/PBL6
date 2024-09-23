package com.pbl6.bookstore.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDTO {
    int id;

    String fullName;

    String phoneNumber;

    String role;

    String email;

    String username;

}
