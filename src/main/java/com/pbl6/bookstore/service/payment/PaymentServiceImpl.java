package com.pbl6.bookstore.service.payment;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.pbl6.bookstore.dto.response.MessageResponse;
import com.pbl6.bookstore.entity.Order;
import com.pbl6.bookstore.entity.PaymentStatus;
import com.pbl6.bookstore.exception.AppException;
import com.pbl6.bookstore.exception.ErrorCode;
import com.pbl6.bookstore.repository.OrderRepository;
import com.pbl6.bookstore.repository.OrderStatusRepository;
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

    OrderStatusRepository orderStatusRepository;

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
            log.info("Confirm webhook URL from PayOS!");
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
        order.get().setOrderStatus(orderStatusRepository.findByName("Chờ xác nhận"));
        orderRepository.save(order.get());
        log.info("Updated payment status of order {}!", order.get().getId());
    }

    @Override
    public void cancelPayment(long orderCode) throws Exception {

        Order order = orderRepository.findById((int) orderCode)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_ID_NOT_FOUND));

        if (!order.getPaymentStatus().equals(PaymentStatus.ERROR.getStatus())) {
            payOS.cancelPaymentLink(orderCode, null);
            order.setPaymentStatus(PaymentStatus.ERROR.getStatus());
            orderRepository.save(order);
        }

    }
}
