package com.ecommerce.aafrincosmetics.dto.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDto {
    private String first_name;

    private String last_name;

    private String userName;

    private String email;

    private String password;

    private String address;

    private Integer phone;
}
