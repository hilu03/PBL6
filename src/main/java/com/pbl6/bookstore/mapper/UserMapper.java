package com.pbl6.bookstore.mapper;

import com.pbl6.bookstore.dto.UserDTO;
import com.pbl6.bookstore.dto.request.UserAccountRequest;
import com.pbl6.bookstore.dto.response.LoginResponseDTO;
import com.pbl6.bookstore.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    LoginResponseDTO toLoginResponseDto(User user);

    UserDTO toUserDTO(User user);

    User toUser(UserAccountRequest request);
}
