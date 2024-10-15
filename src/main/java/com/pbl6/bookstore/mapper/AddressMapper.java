package com.pbl6.bookstore.mapper;

import com.pbl6.bookstore.dto.request.CreateShippingAddressRequest;
import com.pbl6.bookstore.dto.request.UpdateShippingAddressRequest;
import com.pbl6.bookstore.entity.ShippingAddress;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AddressMapper {

    @Mapping(target = "isDefault", ignore = true)
    ShippingAddress toShippingAddress(CreateShippingAddressRequest request);

}
