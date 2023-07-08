package com.ecommerce.aafrincosmetics.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Shipment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String address;

    private String city;

    private String state;

    private String phone;

    private String phone1;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
