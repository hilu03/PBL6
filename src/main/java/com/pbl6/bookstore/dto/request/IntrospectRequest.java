package com.pbl6.bookstore.dto.request;

import lombok.Builder;

@Builder
public class IntrospectRequest {
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
