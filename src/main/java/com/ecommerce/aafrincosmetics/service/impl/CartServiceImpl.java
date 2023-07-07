package com.ecommerce.aafrincosmetics.service.impl;

import com.ecommerce.aafrincosmetics.dto.request.CartRequestDto;
import com.ecommerce.aafrincosmetics.dto.response.CartResponseDto;
import com.ecommerce.aafrincosmetics.dto.response.ProductsResponseDto;
import com.ecommerce.aafrincosmetics.dto.response.UserResponseDto;
import com.ecommerce.aafrincosmetics.entity.Cart;
import com.ecommerce.aafrincosmetics.entity.Products;
import com.ecommerce.aafrincosmetics.entity.User;
import com.ecommerce.aafrincosmetics.repo.CartRepo;
import com.ecommerce.aafrincosmetics.repo.ProductsRepo;
import com.ecommerce.aafrincosmetics.repo.UserRepo;
import com.ecommerce.aafrincosmetics.service.CartService;
import com.ecommerce.aafrincosmetics.service.Others.MiscService;
import com.ecommerce.aafrincosmetics.service.Others.ProductAlreadyExistsException;
import com.ecommerce.aafrincosmetics.service.ProductsService;
import com.ecommerce.aafrincosmetics.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final CartRepo cartRepo;
    private final UserRepo userRepo;
    private final ProductsRepo productsRepo;
    private final MiscService miscService;

    //Add to cart function
    @Override
    public CartResponseDto addProductToCart(CartRequestDto dto) {
        User loggedInUser = miscService.getLoggedInUser();

        //Finding the product
        Products selectedProduct = productsRepo.findById(dto.getProduct_id()).get();

        //Checking the product already exists in the cart for the user
        if(cartRepo.checkIfProductExistsForUserInCart(loggedInUser.getId(),selectedProduct.getId()) != null){
            System.out.println("Dublicate Item");
            //Do Something or redirect to somewhere then throw some exception
            throw new ProductAlreadyExistsException("Product Already exists in the database.");
        }

        //Creating the new cart object
        Cart newCart = new Cart();

        //Adding details to it
        newCart.setQuantity(dto.getQuantity());
        newCart.setProducts(selectedProduct);
        newCart.setUser(loggedInUser);

        return new CartResponseDto(cartRepo.save(newCart));
    }


    //Method to delete the items in the cart
    @Override
    public void deleteItemInCart(Integer id) {
        cartRepo.deleteById(id);
    }

    //Method to get all the items in the cart of the user
    @Override
    public List<CartResponseDto> getAllCartItemsOfUser() {
        User loggedInUser = miscService.getLoggedInUser();

        List<CartResponseDto> allCartItems = new ArrayList<>();

        //Getting the cart item for the logged in user
        List<Cart> foundCartItemsOfUser = cartRepo.findByUser(loggedInUser);

        //Converting the Cart to CartResponseDto
        for(Cart each: foundCartItemsOfUser){
            allCartItems.add(new CartResponseDto(each));
        }
        return allCartItems;
    }

    @Override
    public CartResponseDto updateItemsInCart(Integer cart_id, Integer quantity) {
        //Getting the cart item
        Cart foundItem = cartRepo.findById(cart_id).get();
        //updating the cart quantity
        foundItem.setQuantity(quantity);

        return new CartResponseDto(cartRepo.save(foundItem));
    }

//    //    Method to update the quanity in the cart of the item of a user
//    @Override
//    public CartResponseDto updateItemsInCart(CartRequestDto cartRequestDto, Integer cart_id) {
//        //Getting the cart item
//        Cart foundItem = cartRepo.findById(cart_id).get();
//        //updating the cart quantity
//        foundItem.setQuantity(cartRequestDto.getQuantity());
//
//        return new CartResponseDto(cartRepo.save(foundItem));
//    }

    public Integer getTotalCartValueOfUser(){
        Integer total = 0;
        //Get all the items in the cart
        List<CartResponseDto> allCartItems = getAllCartItemsOfUser();

        //Calculating the total
        for(CartResponseDto each: allCartItems){
            total += (each.getQuantity() * each.getProducts().getPrice());
        }
        return total;
    }
}
