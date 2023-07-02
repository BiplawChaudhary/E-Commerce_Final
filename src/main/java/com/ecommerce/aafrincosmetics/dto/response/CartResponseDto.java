package com.ecommerce.aafrincosmetics.dto.response;


import com.ecommerce.aafrincosmetics.entity.Cart;
import com.ecommerce.aafrincosmetics.entity.Products;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartResponseDto {
    private Integer id;
    private Integer quantity;
    private Products products;

    public CartResponseDto(Cart cart){
        this.id = cart.getId();
        this.quantity = cart.getQuantity();
        this.products = cart.getProducts();
    }

}
