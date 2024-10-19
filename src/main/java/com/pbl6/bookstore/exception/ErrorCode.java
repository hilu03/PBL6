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
    WRONG_VARIABLE_TYPE(MessageResponse.WRONG_VARIABLE_TYPE, HttpStatus.BAD_REQUEST),
    INVALID_GRANT(MessageResponse.INVALID_GRANT, HttpStatus.BAD_REQUEST),
    INVALID_TOKEN(MessageResponse.INVALID_TOKEN, HttpStatus.BAD_REQUEST),
    BOOK_ID_NOT_FOUND(MessageResponse.BOOK_IDs_NOT_FOUND, HttpStatus.NOT_FOUND),
    QUANTITY_EXCEED(MessageResponse.QUANTITY_EXCEED, HttpStatus.BAD_REQUEST),
    INVALID_QUANTITY(MessageResponse.INVALID_QUANTITY, HttpStatus.BAD_REQUEST),
    INVALID_REQUEST_DATA(MessageResponse.INVALID_REQUEST_DATA, HttpStatus.BAD_REQUEST),
    ITEM_NOT_FOUND(MessageResponse.ITEM_NOT_FOUND, HttpStatus.NOT_FOUND),
    PAYMENT_METHOD_NOT_FOUND(MessageResponse.PAYMENT_METHOD_NOT_FOUND, HttpStatus.NOT_FOUND),
    ADDRESS_NOT_FOUND(MessageResponse.ADDRESS_NOT_FOUND, HttpStatus.NOT_FOUND),
    ORDER_ID_NOT_FOUND(MessageResponse.ORDER_ID_NOT_FOUND, HttpStatus.NOT_FOUND),
    DUPLICATED_ADDRESS(MessageResponse.DUPLICATED_ADDRESS, HttpStatus.BAD_REQUEST),
    CONFLICT_DEFAULT_ADDRESS(MessageResponse.CONFLICT_DEFAULT_ADDRESS, HttpStatus.BAD_REQUEST),
    ORDER_STATUS_ID_NOT_FOUND(MessageResponse.ORDER_STATUS_ID_NOT_FOUND, HttpStatus.NOT_FOUND)
    ;

    ErrorCode(String message, HttpStatusCode httpStatusCode) {
        this.message = message;
        this.httpStatusCode = httpStatusCode;
    }

    private final String message;
    private final HttpStatusCode httpStatusCode;

}
