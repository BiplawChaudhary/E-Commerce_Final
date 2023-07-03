package com.ecommerce.aafrincosmetics.dto.response;


import com.ecommerce.aafrincosmetics.entity.Products;
import com.ecommerce.aafrincosmetics.entity.Wishlist;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WishlistResponseDto {
    private Integer id;
    private Products products;

    public WishlistResponseDto(Wishlist wishlist){
        this.id = wishlist.getId();
        this.products = wishlist.getProducts();
    }
}
