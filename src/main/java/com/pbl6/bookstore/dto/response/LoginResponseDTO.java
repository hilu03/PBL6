package com.pbl6.bookstore.dto.response;


import lombok.Data;

@Data
public class LoginResponseDTO {

    private String token;

    private String fullName;

    private String role;

    private String email;

    private String username;

}
