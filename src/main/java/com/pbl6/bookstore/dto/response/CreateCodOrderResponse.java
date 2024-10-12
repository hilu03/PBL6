package com.pbl6.bookstore.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateCodOrderResponse {

    int orderID;

    String orderStatus;

    String receiver;

    String phoneNumber;

    String address;

    String dateOrder;

    String paymentMethod;

    String paymentStatus;



}
