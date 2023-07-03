package com.ecommerce.aafrincosmetics.controller;

import com.ecommerce.aafrincosmetics.dto.request.CartRequestDto;
import com.ecommerce.aafrincosmetics.service.CategoryService;
import com.ecommerce.aafrincosmetics.service.Others.MiscService;
import com.ecommerce.aafrincosmetics.service.WishlistService;
import com.ecommerce.aafrincosmetics.service.ProductsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class HomePageController {

    private final CategoryService categoryService;
    private final ProductsService productsService;

    private final WishlistService wishlistService;
    private final MiscService miscService;


    //Getting the Homepage
    @GetMapping("/")
    public String getHomePage(Model model) {
        model.addAttribute("allCategory", categoryService.getAllCategory());
        model.addAttribute("allProduct", productsService.getAllProducts());

        //If user is logged in add their wishlist to the index page
        if(miscService.isUserLoggedIn()){
            model.addAttribute("wishlist", wishlistService.allwishlistItemsOfUser());
        }

        //Adding the cart dto the main page
        model.addAttribute("cartdto", new CartRequestDto());

        return "demo/index";
    }

    //Mapping for checkout


}
