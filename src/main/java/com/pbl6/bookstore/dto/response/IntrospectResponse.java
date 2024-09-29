package com.pbl6.bookstore.dto.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class IntrospectResponse {
    private boolean valid;

    public IntrospectResponse(boolean valid) {
        this.valid = valid;
    }

}
