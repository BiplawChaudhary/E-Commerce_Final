package com.ecommerce.aafrincosmetics.dto.response;


import com.ecommerce.aafrincosmetics.entity.Category;
import com.ecommerce.aafrincosmetics.entity.Images;
import com.ecommerce.aafrincosmetics.entity.Products;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductsResponseDto {
    private Integer id;
    private String productName;
    private String description;
    private Integer stock;
    private Integer price;
    private Images images;
    private Category category;
    private String manufacturer;

    public ProductsResponseDto(Products product){
        this.id = product.getId();
        this.productName = product.getProductName();
        this.description = product.getDescription();
        this.stock = product.getStock();
        this.price = product.getPrice();
        this.images = product.getImages();
        this.category = product.getCategory();
        this.manufacturer = product.getManufacturer();
    }
}
