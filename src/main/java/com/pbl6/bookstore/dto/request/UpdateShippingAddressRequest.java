package com.pbl6.bookstore.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateShippingAddressRequest {

    @NotNull
    int id;

    @NotNull
    String receiver;

    @NotNull
    String phoneNumber;

    @NotNull
    String address;

    @NotNull
    String city;

    @NotNull
    String district;

    @NotNull
    String ward;

    Boolean isDefault;

}
