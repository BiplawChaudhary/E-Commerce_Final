package com.ecommerce.aafrincosmetics.service;

import com.ecommerce.aafrincosmetics.dto.request.UserRequestDto;
import com.ecommerce.aafrincosmetics.dto.response.UserResponseDto;

public interface UserService {

    UserResponseDto saveUserToTable(UserRequestDto userRequestDto);

    UserResponseDto findUserByUserName(String username);
}
