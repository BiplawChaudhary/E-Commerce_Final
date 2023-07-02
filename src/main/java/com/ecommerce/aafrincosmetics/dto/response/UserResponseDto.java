package com.ecommerce.aafrincosmetics.dto.response;

import com.ecommerce.aafrincosmetics.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {
    private Integer id;
    private String first_name;

    private String last_name;

    private String username;

    private String email;

    private String password;

    private String address;

    private Integer phone;

    public UserResponseDto(User user){
        this.id = user.getId();
        this.first_name = user.getFirst_name();
        this.last_name = user.getLast_name();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.address = user.getPassword();
        this.phone = user.getPhone();
    }
}
