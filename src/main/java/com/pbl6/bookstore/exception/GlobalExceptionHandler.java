package com.pbl6.bookstore.exception;

import com.pbl6.bookstore.dto.response.APIResponse;
import com.pbl6.bookstore.dto.response.MessageResponse;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import vn.payos.exception.PayOSException;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    ResponseEntity<APIResponse> handleGlobalException(Exception exception) {
        log.error("Exception: {}", String.valueOf(exception));

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
        log.error("Exception: {}", exception.getMessage());
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

    @ExceptionHandler(value = PayOSException.class)
    public ResponseEntity<APIResponse> handlePayOSException(PayOSException exception) {
        log.error("Error code: {}", exception.getCode());
        log.error("Message: {}", exception.getMessage());
        if (exception.getCode().equals("401")) {
            return ResponseEntity.status(ErrorCode.UNAUTHENTICATED.getHttpStatusCode())
                    .body(new APIResponse(ErrorCode.UNAUTHENTICATED.getMessage(), null));
        }
        return ResponseEntity.status(ErrorCode.INVALID_REQUEST_DATA.getHttpStatusCode())
                .body(new APIResponse(ErrorCode.INVALID_REQUEST_DATA.getMessage(), null));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<APIResponse> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {

        if (ex.getMessage().contains("UniqueAddress")) {
            return ResponseEntity.status(ErrorCode.DUPLICATED_ADDRESS.getHttpStatusCode())
                    .body(new APIResponse(ErrorCode.DUPLICATED_ADDRESS.getMessage(), null));
        }
        else if (ex.getMessage().contains("UniqueDefaultAddress")) {
            return ResponseEntity.status(ErrorCode.DEFAULT_ADDRESS_EXIST.getHttpStatusCode())
                    .body(new APIResponse(ErrorCode.DEFAULT_ADDRESS_EXIST.getMessage(), null));
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new APIResponse(MessageResponse.SERVER_ERROR, null));
    }

}
