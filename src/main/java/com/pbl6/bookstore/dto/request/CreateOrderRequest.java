package com.pbl6.bookstore.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateOrderRequest {

    @NotNull
    List<ItemRequestDTO> items;

    @NotNull
    int paymentMethodID;

    @NotNull
    Integer shippingAddressID;

}
