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
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderController {

    OrderServiceImpl orderService;

    @PostMapping("/create")
    public ResponseEntity<APIResponse> createOrder(@RequestBody @Valid CreateOrderRequest request)
            throws Exception {
        return ResponseEntity.ok(new APIResponse(MessageResponse.ORDER_CREATE_SUCCESS,
                orderService.createOrder(request)));
    }

    @GetMapping("/payment-status/{orderID}")
    public ResponseEntity<APIResponse> getOrderPaymentStatus(@PathVariable int orderID) {
        return ResponseEntity.ok(new APIResponse(orderService.getOrderPaymentStatus(orderID), null));
    }

    @GetMapping("/payment-method")
    public ResponseEntity<APIResponse> getAllPaymentMethod() {
        return ResponseEntity.ok(new APIResponse(MessageResponse.RESOURCE_FOUND,
                orderService.getAllPaymentMethod()));
    }

    @GetMapping("/get-all-status")
    public ResponseEntity<APIResponse> getAllOrderStatus() {
        return ResponseEntity.ok(new APIResponse(MessageResponse.RESOURCE_FOUND,
                orderService.getAllOrderStatus()));
    }

    @GetMapping("/status/{statusID}")
    public ResponseEntity<APIResponse> getOrderByStatusID(@PathVariable int statusID) {
        return ResponseEntity.ok(new APIResponse(MessageResponse.RESOURCE_FOUND,
                orderService.getOrderByStatusID(statusID)));
    }

    @GetMapping("/{orderID}")
    public ResponseEntity<APIResponse> getOrderByOrderID(@PathVariable int orderID) {
        return ResponseEntity.ok(new APIResponse(MessageResponse.RESOURCE_FOUND,
                orderService.getOrderByOrderID(orderID)));
    }

}
