package com.pbl6.bookstore.service.payment;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.pbl6.bookstore.dto.response.MessageResponse;
import com.pbl6.bookstore.entity.Order;
import com.pbl6.bookstore.entity.PaymentStatus;
import com.pbl6.bookstore.repository.OrderRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vn.payos.PayOS;
import vn.payos.type.Webhook;
import vn.payos.type.WebhookData;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PaymentServiceImpl implements PaymentService {

    PayOS payOS;

    OrderRepository orderRepository;

    @Override
    public void handlePayOSTransfer(ObjectNode body) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        Webhook webhookBody = objectMapper.treeToValue(body, Webhook.class);
        WebhookData webhookData = payOS
                .verifyPaymentWebhookData(webhookBody);
//        log.info("webhookData: {}", webhookData);

        // check if it's first time confirm webhook URL to payOS
        if (webhookData.getDescription().equals("VQRIO123")
            && webhookData.getAccountNumber().equals("12345678")) {
            return;
        }

        Optional<Order> order = orderRepository
                .findById(webhookData.getOrderCode().intValue());

        if (order.isEmpty()) {
            log.warn(MessageResponse.ORDER_CODE_NOT_FOUND);
            return;
        }

        if (order.get().getPaymentStatus()
                .equals(PaymentStatus.COMPLETED.getStatus())) {
            log.warn(MessageResponse.ORDER_ALREADY_PAID);
            return;
        }

        order.get().setPaymentStatus(PaymentStatus.COMPLETED.getStatus());
        orderRepository.save(order.get());
        log.info("Updated payment status of order {}!", order.get().getId());
    }
}
