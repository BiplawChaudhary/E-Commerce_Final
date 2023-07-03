package com.ecommerce.aafrincosmetics.service;

import com.ecommerce.aafrincosmetics.dto.response.WishlistResponseDto;

import java.util.List;

public interface WishlistService {

    //Add item to wishlist
    WishlistResponseDto addProductToWishlist(Integer product_id);

    void deleteItemFromWishlist(Integer id);

    List<WishlistResponseDto> allwishlistItemsOfUser();
}
