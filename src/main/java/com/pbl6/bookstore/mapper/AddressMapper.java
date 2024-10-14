package com.pbl6.bookstore.mapper;

import com.pbl6.bookstore.dto.UserDTO;
import com.pbl6.bookstore.dto.request.ShippingAddressRequest;
import com.pbl6.bookstore.dto.request.UserAccountRequest;
import com.pbl6.bookstore.dto.response.LoginResponseDTO;
import com.pbl6.bookstore.entity.ShippingAddress;
import com.pbl6.bookstore.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AddressMapper {

    @Mapping(target = "isDefault", ignore = true)
    ShippingAddress toShippingAddress(ShippingAddressRequest request);
}
