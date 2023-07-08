package com.ecommerce.aafrincosmetics.dto.request;

import com.ecommerce.aafrincosmetics.entity.Category;
import com.ecommerce.aafrincosmetics.entity.Images;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductsRequestDto {
    private String productName;
    private String description;
    private Integer stock;
    private Integer price;
    private Category category;
    private Images images;
    private String manufacturer;
}
