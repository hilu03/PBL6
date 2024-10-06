package com.pbl6.bookstore.exception;

import com.pbl6.bookstore.dto.response.APIResponse;
import com.pbl6.bookstore.dto.response.MessageResponse;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    ResponseEntity<APIResponse> handleGlobalException(Exception exception) {
        log.error("Exception: " + exception);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new APIResponse(MessageResponse.SERVER_ERROR, null));
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    ResponseEntity<APIResponse> handleAccessDeniedException(AccessDeniedException exception) {
        return ResponseEntity.status(ErrorCode.DENIED_PERMISSION.getHttpStatusCode())
                .body(new APIResponse(ErrorCode.DENIED_PERMISSION.getMessage(), null));
    }

    @ExceptionHandler(value = AppException.class)
    ResponseEntity<APIResponse> handleAppException(AppException exception) {
        return ResponseEntity.status(exception.getErrorCode().getHttpStatusCode())
                .body(new APIResponse(exception.getErrorCode().getMessage(), null));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<APIResponse> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {

        return ResponseEntity.status(ErrorCode.WRONG_VARIABLE_TYPE.getHttpStatusCode())
                .body(new APIResponse(ErrorCode.WRONG_VARIABLE_TYPE.getMessage(), null));
    }

    @ExceptionHandler(FeignException.BadRequest.class)
    public ResponseEntity<APIResponse> handleInvalidGrantException(FeignException.BadRequest e) {

        return ResponseEntity.status(ErrorCode.INVALID_GRANT.getHttpStatusCode())
                .body(new APIResponse(ErrorCode.INVALID_GRANT.getMessage(), null));
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<APIResponse> handleInvalidArgument(MethodArgumentNotValidException exception) {
        return ResponseEntity.status(ErrorCode.INVALID_QUANTITY.getHttpStatusCode())
                .body(new APIResponse(ErrorCode.INVALID_QUANTITY.getMessage(), null));
    }

    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public ResponseEntity<APIResponse> handleHttpMessageNotReadable(HttpMessageNotReadableException exception) {
        return ResponseEntity.status(ErrorCode.INVALID_REQUEST_DATA.getHttpStatusCode())
                .body(new APIResponse(ErrorCode.INVALID_REQUEST_DATA.getMessage(), null));
    }

}
