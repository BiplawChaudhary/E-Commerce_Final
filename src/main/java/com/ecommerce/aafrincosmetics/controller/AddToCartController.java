package com.ecommerce.aafrincosmetics.controller;

import com.ecommerce.aafrincosmetics.dto.request.CartRequestDto;
import com.ecommerce.aafrincosmetics.dto.response.CartResponseDto;
import com.ecommerce.aafrincosmetics.service.CartService;
import com.ecommerce.aafrincosmetics.service.Others.MiscService;
import com.ecommerce.aafrincosmetics.service.Others.ProductAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AddToCartController {
    private final CartService cartService;
    private final MiscService miscService;


    //-------------------- Add to cart --------------------------------
    /*
    FOr add to cart function pass the cartDto with fields qunatity, product_id to the cutomer
    then bind those values there and then save them to the able.
    * */
    @PostMapping("/add-to-cart/{product_id}")
    public String addToCartFunction(@ModelAttribute CartRequestDto cartRequestDto, @PathVariable("product_id") Integer product_id,
                                    RedirectAttributes redirectAttributes){
        //If the cart value is null then:
        if(cartRequestDto.getQuantity() == null){
            redirectAttributes.addAttribute("nullQuantityError", "Please select some quantity.");
            return "redirect:/";
        }
        //Only allowing to add to cart if authenticated
        if(miscService.isUserLoggedIn()){
            try{
                cartRequestDto.setProduct_id(product_id);

                cartService.addProductToCart(cartRequestDto);
                return "redirect:/";
            }catch (ProductAlreadyExistsException ex){
                redirectAttributes.addAttribute("duplicateError", "Product Already Added");
                return "redirect:/";
            }
        }else{
            return "redirect:/login";
        }

    }


    //Show the items in the cart -- Cart's Page
    @GetMapping("/my-cart")
    public String getMyCart(Model model, @ModelAttribute("deleteMsg") String deleteMsg){

        //If the user is logged in
        if(miscService.isUserLoggedIn()){
            List<CartResponseDto> allCartItems = cartService.getAllCartItemsOfUser();

            model.addAttribute("cartItems",allCartItems );

            model.addAttribute("deleteMsg", deleteMsg);
            model.addAttribute("total", cartService.getTotalCartValueOfUser());
            return "main/cart";
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
