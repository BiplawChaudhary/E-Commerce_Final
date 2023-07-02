package com.ecommerce.aafrincosmetics.controller;

import com.ecommerce.aafrincosmetics.dto.request.CartRequestDto;
import com.ecommerce.aafrincosmetics.service.CartService;
import com.ecommerce.aafrincosmetics.service.CategoryService;
import com.ecommerce.aafrincosmetics.service.Others.MiscService;
import com.ecommerce.aafrincosmetics.service.ProductsService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class HomePageController {

    private final CategoryService categoryService;
    private final ProductsService productsService;
    private final CartService cartService;
    private final MiscService miscService;

    //Getting the Homepage
    @GetMapping("/")
    public String getHomePage(Model model) {
//        if(productsService.getProductByCategory().size() !=0){
//            model.addAttribute("allCategory",categoryService.getAllCategory());
//            model.addAttribute("allProduct", productsService.getProductByCategory());
//        }
        model.addAttribute("allCategory", categoryService.getAllCategory());
        model.addAttribute("allProduct", productsService.getAllProducts());

        //Adding the cart dto the main page
        model.addAttribute("cartdto", new CartRequestDto());

        return "demo/index";
    }

    //-------------------- Add to cart --------------------------------
    /*
    FOr add to cart function pass the cartDto with fields qunatity, product_id to the cutomer
    then bind those values there and then save them to the able.
    * */
    @PostMapping("/add-to-cart/{product_id}")
    public String addToCartFunction(@ModelAttribute CartRequestDto cartRequestDto, @PathVariable("product_id") Integer product_id){
        //Only allowing to add to cart if authenticated
        if(miscService.isUserLoggedIn()){
            cartRequestDto.setProduct_id(product_id);

            cartService.addProductToCart(cartRequestDto);
            return "redirect:/";
        }else{
            return "redirect:/login";
        }

    }


    //Show the items in the cart -- Cart's Page
    @GetMapping("/my-cart")
    public String getMyCart(Model model, @ModelAttribute("deleteMsg") String deleteMsg){
        //If the user is logged in
        if(miscService.isUserLoggedIn()){
            model.addAttribute("cartItems", cartService.getAllCartItemsOfUser());

            model.addAttribute("deleteMsg", deleteMsg);
            return "demo/cart";
        }else{
            return "redirect:/login";
        }
    }

    //Delete the items in the cart
    //Since user will only see this button if cart's page, which can only be accessed
    //when logged, in no need to check if the user is logged in.
    @GetMapping("/delete-item/{id}")
    public String deleteItemInCart(@PathVariable Integer id, RedirectAttributes redirectAttributes){
        cartService.deleteItemInCart(id);
        redirectAttributes.addAttribute("deleteMsg", "Item deleted.");
        return "redirect:/my-cart";
    }

//    @GetMapping("/update-item/{id}")
//    public String updateItemInCart(@PathVariable Integer id, @RequestParam("quantity")Integer quantity){
//        cartService.updateItemsInCart(id,quantity);
//        return "redirect:/my-cart";
//    }


}
