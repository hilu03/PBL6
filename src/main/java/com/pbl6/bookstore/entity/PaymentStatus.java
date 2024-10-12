package com.pbl6.bookstore.entity;

import lombok.Getter;

@Getter
public enum PaymentStatus {
    PENDING("Chưa thanh toán"),
    COMPLETED("Đã thanh toán"),
    ERROR("Thanh toán không thành công"),
    EXPIRED("Hết hạn")
    ;

    PaymentStatus(String status) {
        this.status = status;
    }

    private final String status;

}
