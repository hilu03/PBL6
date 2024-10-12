package com.pbl6.bookstore.controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.pbl6.bookstore.dto.response.APIResponse;
import com.pbl6.bookstore.dto.response.WebhookResponse;
import com.pbl6.bookstore.service.payment.PaymentServiceImpl;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.payos.PayOS;
import vn.payos.type.*;


@Slf4j
@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PaymentController {

    PayOS payOS;

    PaymentServiceImpl paymentService;

    @NonFinal
    String returnUrl = "http://localhost:8080/api/payment/success";

    @NonFinal
    String cancelUrl = "http://localhost:8080/api/payment/cancel";


    @PostMapping("/create/{orderCode}")
    ResponseEntity<APIResponse> createPayment(@PathVariable long orderCode) throws Exception {
        ItemData itemData = ItemData.builder().name("Harry Porter")
                .quantity(1).price(2000).build();

        PaymentData paymentData = PaymentData.builder().orderCode(orderCode).amount(2000)
                .description("Pay").returnUrl(returnUrl)
                .cancelUrl(cancelUrl).item(itemData).build();

//        String confirmResult = payOS.confirmWebhook("https://3d43-171-238-155-246.ngrok-free.app/api/payment/status");
//        System.out.println(confirmResult);

        CheckoutResponseData result = payOS.createPaymentLink(paymentData);
        return ResponseEntity.ok(new APIResponse("", result));
    }

    @GetMapping("/success")
    String success() {
        return "success";
    }

    @GetMapping("/cancel")
    String cancel() {
        return "cancel";
    }

    @PostMapping("/cancel/{orderCode}")
    public void cancelUrl(@PathVariable long orderCode) throws Exception {
        payOS.cancelPaymentLink(orderCode, null);
    }

    @PostMapping("/handle-transfer")
    WebhookResponse handlePaymentTransfer(@RequestBody ObjectNode body) throws Exception {
        log.info("Called from PayOS!");
        log.info("Request body: {}", body);

        paymentService.handlePayOSTransfer(body);

        return WebhookResponse.builder()
                .success(true)
                .build();
    }

}
