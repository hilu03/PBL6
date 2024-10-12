package com.pbl6.bookstore.controller;

import com.pbl6.bookstore.dto.request.CreateOrderRequest;
import com.pbl6.bookstore.dto.response.APIResponse;
import com.pbl6.bookstore.dto.response.MessageResponse;
import com.pbl6.bookstore.service.order.OrderServiceImpl;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderController {

    OrderServiceImpl orderService;

    @PostMapping("/order/create")
    public ResponseEntity<APIResponse> createOrder(@RequestBody @Valid CreateOrderRequest request)
            throws Exception {
        return ResponseEntity.ok(new APIResponse(MessageResponse.ORDER_CREATE_SUCCESS,
                orderService.createOrder(request)));
    }

}
