package com.ecommerce.aafrincosmetics.controller;


import com.ecommerce.aafrincosmetics.dto.request.CartRequestDto;
import com.ecommerce.aafrincosmetics.dto.response.ProductsResponseDto;
import com.ecommerce.aafrincosmetics.dto.response.WishlistResponseDto;
import com.ecommerce.aafrincosmetics.service.CartService;
import com.ecommerce.aafrincosmetics.service.CategoryService;
import com.ecommerce.aafrincosmetics.service.Others.MiscService;
import com.ecommerce.aafrincosmetics.service.Others.ProductAlreadyExistsException;
import com.ecommerce.aafrincosmetics.service.ProductsService;
import com.ecommerce.aafrincosmetics.service.WishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class WishlistController {
    private final WishlistService wishlistService;
    private final CartService cartService;
    private final MiscService miscService;
    private final CategoryService categoryService;


    //********* ------------- WIshlist COntroller ---------

    @GetMapping("/my-wishlist")
    public String getMyWishlist(Model model){

        if(miscService.isUserLoggedIn()){
            model.addAttribute("cartValue", cartService.getTotalCartValueOfUser());
            model.addAttribute("allItems", wishlistService.allwishlistItemsOfUser());
            model.addAttribute("cartdto", new CartRequestDto());
            model.addAttribute("allCategory", categoryService.getAllCategory());
            return "main/wishlist";
        }else{
            return "redirect:/login";
        }
    }

    @GetMapping("/add-to-wishlist/{id}")
    public String addItemToWishlist(@PathVariable("id") Integer product_id, Model model){

        if(miscService.isUserLoggedIn()){
            model.addAttribute("cartValue", cartService.getTotalCartValueOfUser());
//                If not alreadyin wishlist then add it
            try{
                WishlistResponseDto savedWishlistItem= wishlistService.addProductToWishlist(product_id);
                return "redirect:/#"+savedWishlistItem.getProducts().getCategory().getCategoryName();

            }catch (ProductAlreadyExistsException ex){
                System.out.println("Product Already Exists.");
                return "redirect:/";
            }
        }
        else{
            return "redirect:/login";
        }
    }

    //Delete the items in wishlist
    @GetMapping("delete-wishlist/{id}")
    public String deleteFromWishlist(@PathVariable Integer id){
        wishlistService.deleteItemFromWishlist(id);
        return "redirect:/my-wishlist";
    }
}
