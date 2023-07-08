package com.ecommerce.aafrincosmetics.dto.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShipmentRequestDto {
    private String address;

    private String city;

    private String state;

    private String phone;

    private String phone1;
}
