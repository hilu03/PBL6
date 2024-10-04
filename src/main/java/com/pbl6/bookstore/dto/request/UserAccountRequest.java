package com.pbl6.bookstore.dto.request;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserAccountRequest {
    String fullName;

    String phoneNumber;

    String password;

    String email;

    String username;

}
