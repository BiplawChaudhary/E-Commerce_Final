package com.ecommerce.aafrincosmetics.service;

import com.ecommerce.aafrincosmetics.dto.request.CartRequestDto;
import com.ecommerce.aafrincosmetics.dto.response.CartResponseDto;

import java.util.List;

public interface CartService {


    //Method to add products to cart for a logged in user
    CartResponseDto addProductToCart(CartRequestDto dto);

    //Method to delete the products in the cart
    void deleteItemInCart(Integer id);

    //Method to retrieve all the items in the cart for a user
    List<CartResponseDto> getAllCartItemsOfUser();

    //Method to update the quantity in the cart
    CartResponseDto updateItemsInCart( Integer cart_id, Integer quantity);

    Integer getTotalCartValueOfUser();
}
