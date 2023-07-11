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
@Table(name = "products")
public class Products {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

//    @Column(unique = true)
    private String productName;

    @Column(length = 2000)
    private String description;

    private Integer stock;

    private Integer price;

    private String manufacturer;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToOne
    @JoinColumn(name = "image_id")
    private Images images;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

}
