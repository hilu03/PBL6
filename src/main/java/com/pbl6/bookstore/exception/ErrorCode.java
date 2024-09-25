package com.pbl6.bookstore.exception;

import com.pbl6.bookstore.dto.response.MessageResponse;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    RESOURCE_NOT_FOUND(MessageResponse.RESOURCE_NOT_FOUND, HttpStatus.NOT_FOUND),
    INVALID_PAGE_NUMBER(MessageResponse.INVALID_PAGE_NUMBER, HttpStatus.BAD_REQUEST),
    LOGIN_FAIL(MessageResponse.LOGIN_FAIL, HttpStatus.UNAUTHORIZED),
    USER_NOT_FOUND(MessageResponse.USER_NOT_FOUND, HttpStatus.NOT_FOUND),
    USER_EXISTED(MessageResponse.USER_EXISTED, HttpStatus.CONFLICT),
    UNAUTHENTICATED(MessageResponse.UNAUTHENTICATED, HttpStatus.UNAUTHORIZED),
    DENIED_PERMISSION(MessageResponse.DENIED_PERMISSION, HttpStatus.FORBIDDEN),
    WRONG_VARIABLE_TYPE(MessageResponse.WRONG_VARIABLE_TYPE, HttpStatus.BAD_REQUEST)
    ;

    ErrorCode(String message, HttpStatusCode httpStatusCode) {
        this.message = message;
        this.httpStatusCode = httpStatusCode;
    }

    private final String message;
    private final HttpStatusCode httpStatusCode;

}
