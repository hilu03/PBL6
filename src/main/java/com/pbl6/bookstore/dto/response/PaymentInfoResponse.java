package com.pbl6.bookstore.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaymentInfoResponse {
    
    String bankName;

    String bankShortName;
    
    String accountName;
    
    String accountNumber;
    
    Integer amount;
    
    String description;
}
