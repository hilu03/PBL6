package com.pbl6.bookstore.service.payment;

import com.fasterxml.jackson.databind.node.ObjectNode;

public interface PaymentService {

    void handlePayOSTransfer(ObjectNode body) throws Exception;

    void cancelPayment(long orderCode) throws Exception;

}
