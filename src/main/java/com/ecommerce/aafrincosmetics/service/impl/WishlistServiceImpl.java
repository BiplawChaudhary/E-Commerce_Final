package com.ecommerce.aafrincosmetics.service.impl;

import com.ecommerce.aafrincosmetics.dto.response.WishlistResponseDto;
import com.ecommerce.aafrincosmetics.entity.Products;
import com.ecommerce.aafrincosmetics.entity.User;
import com.ecommerce.aafrincosmetics.entity.Wishlist;
import com.ecommerce.aafrincosmetics.repo.ProductsRepo;
import com.ecommerce.aafrincosmetics.repo.WishlistRepo;
import com.ecommerce.aafrincosmetics.service.Others.MiscService;
import com.ecommerce.aafrincosmetics.service.Others.ProductAlreadyExistsException;
import com.ecommerce.aafrincosmetics.service.WishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class WishlistServiceImpl implements WishlistService {
    private final MiscService miscService;
    private final ProductsRepo productsRepo;
    private final WishlistRepo wishlistRepo;


    //Method to save the item to wishlist
    @Override
    public WishlistResponseDto addProductToWishlist(Integer product_id) {
        //Getting the logged-in user
        User loggedInUser = miscService.getLoggedInUser();

        //Getting the product
        Products foundProduct = productsRepo.findById(product_id).get();

        //git gChecking if the item exists in wishlist for user
        if(wishlistRepo.checkIfProductExistsForUserInWishlist(loggedInUser.getId(), foundProduct.getId()) != null){
            System.out.println("Duplicate Item");
            //Do Something here
            throw new ProductAlreadyExistsException("Product already exists in the wishlist");
        }

        //Creating new wishlist and adding items to it.
        Wishlist newWishlist = new Wishlist();
        newWishlist.setUser(loggedInUser);
        newWishlist.setProducts(foundProduct);

        //Saving it
        return new WishlistResponseDto(wishlistRepo.save(newWishlist));

    }

    @Override
    public void deleteItemFromWishlist(Integer id) {
        wishlistRepo.deleteById(id);
    }

//    Method to return all the wishlist items of the logged in user
    @Override
    public List<WishlistResponseDto> allwishlistItemsOfUser() {
        //Getting the loged in user
        User loggedInUser = miscService.getLoggedInUser();


        List<WishlistResponseDto> returnList = new ArrayList<>();

        List<Wishlist> allItems = wishlistRepo.findByUser(loggedInUser);

        for(Wishlist each: allItems){
            returnList.add(new WishlistResponseDto(each));
        }

        return returnList;
    }
}
