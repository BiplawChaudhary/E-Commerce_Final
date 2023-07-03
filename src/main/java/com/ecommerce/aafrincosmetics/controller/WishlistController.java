package com.ecommerce.aafrincosmetics.controller;


import com.ecommerce.aafrincosmetics.service.Others.MiscService;
import com.ecommerce.aafrincosmetics.service.Others.WishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class WishlistController {
    private final WishlistService wishlistService;
    private final MiscService miscService;


    //********* ------------- WIshlist COntroller ---------

    @GetMapping("/my-wishlist")
    public String getMyWishlist(Model model){

        if(miscService.isUserLoggedIn()){
            model.addAttribute("allItems", wishlistService.allwishlistItemsOfUser());
            return "demo/wishlist";
        }else{
            return "redirect:/login";
        }
    }

    @GetMapping("/add-to-wishlist/{id}")
    public String addItemToWIshlist(@PathVariable("id") Integer product_id){
        if(miscService.isUserLoggedIn()){
            wishlistService.addProductToWishlist(product_id);
            return "redirect:/";
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
