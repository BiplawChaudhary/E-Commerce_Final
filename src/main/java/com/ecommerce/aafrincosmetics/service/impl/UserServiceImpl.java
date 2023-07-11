package com.ecommerce.aafrincosmetics.service.impl;

import com.ecommerce.aafrincosmetics.dto.request.UserRequestDto;
import com.ecommerce.aafrincosmetics.dto.response.UserResponseDto;
import com.ecommerce.aafrincosmetics.entity.User;
import com.ecommerce.aafrincosmetics.repo.RoleRepo;
import com.ecommerce.aafrincosmetics.repo.UserRepo;
import com.ecommerce.aafrincosmetics.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepo userRepo;
    private final RoleRepo roleRepo;


    @Override
    public UserResponseDto saveUserToTable(UserRequestDto dto) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        User newUser = new User();

        newUser.setFirst_name(dto.getFirst_name());
        newUser.setLast_name(dto.getLast_name());
        newUser.setUsername(dto.getUserName());
        newUser.setPassword(encoder.encode(dto.getPassword().trim()));
        newUser.setEmail(dto.getEmail());
        newUser.setPhone(dto.getPhone());
        newUser.setAddress(dto.getAddress());

        newUser.setRoles(roleRepo.getUserRole("USER"));

        User savedUser = userRepo.save(newUser);

        return new UserResponseDto(savedUser);
    }

    @Override
    public UserResponseDto findUserByUserName(String username) {
        return new UserResponseDto(userRepo.getUserByUsername(username));
    }
}
