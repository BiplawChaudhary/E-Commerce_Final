package com.ecommerce.aafrincosmetics.controller;

import com.ecommerce.aafrincosmetics.dto.request.CartRequestDto;
import com.ecommerce.aafrincosmetics.dto.request.ShipmentRequestDto;
import com.ecommerce.aafrincosmetics.dto.response.CartResponseDto;
import com.ecommerce.aafrincosmetics.service.*;
import com.ecommerce.aafrincosmetics.service.Others.MiscService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomePageController {

    private final CategoryService categoryService;
    private final ProductsService productsService;

    private final WishlistService wishlistService;
    private final MiscService miscService;

    private final ShipmentService shipmentService;
    private final CartService cartService;


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
    @GetMapping("/checkout")
    public String getCheckoutPage(Model model){
        if(miscService.isUserLoggedIn()){
            model.addAttribute("shipmentDetails", shipmentService.getAllShipmentDetails());
            //Getting all the cart items
            List<CartResponseDto> allCartItems =  cartService.getAllCartItemsOfUser();

            Integer total=0;

            for(CartResponseDto each: allCartItems){
                total += each.getQuantity() * each.getProducts().getPrice();
            }

//            model.addAttribute("cartItems",allCartItems);
            model.addAttribute("totalPrice", total);
            return "demo/checkout";
        }else{
            return "redirect:/login";
        }
    }

    @GetMapping("/get-add-address-form")
    public String getAddAddressForm(Model model){
        model.addAttribute("detail", new ShipmentRequestDto());
        return "demo/detailsForm";
    }

    @PostMapping("/get-add-address-form")
    public String saveData(@ModelAttribute ShipmentRequestDto dto){
        shipmentService.saveShipmentToTable(dto);
        return "redirect:/checkout";
    }

    //Delete the shipment address
//    Place this delete somewhere like in add form
    @GetMapping("/delete-shipping-address/{id}")
    public String deleteTheAddress(@PathVariable("id") Integer id){
        shipmentService.deleteShipmentDetails(id);
        return "redirect:/checkout";
    }



}
