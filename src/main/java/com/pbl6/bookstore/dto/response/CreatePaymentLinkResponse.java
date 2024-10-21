package com.pbl6.bookstore.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreatePaymentLinkResponse {

    int orderID;

    String checkoutUrl;

    String qrCode;

    PaymentInfoResponse paymentInfo;

}
