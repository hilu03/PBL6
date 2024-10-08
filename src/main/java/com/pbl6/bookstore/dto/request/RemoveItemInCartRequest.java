package com.pbl6.bookstore.dto.request;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RemoveItemInCartRequest {
    List<String> bookIDs;
}
