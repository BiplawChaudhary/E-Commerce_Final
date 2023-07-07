package com.ecommerce.aafrincosmetics.controller;


import com.ecommerce.aafrincosmetics.dto.request.ShipmentRequestDto;
import com.ecommerce.aafrincosmetics.dto.response.CartResponseDto;
import com.ecommerce.aafrincosmetics.entity.Order;
import com.ecommerce.aafrincosmetics.entity.OrderItems;
import com.ecommerce.aafrincosmetics.service.CartService;
import com.ecommerce.aafrincosmetics.service.OrderItemsService;
import com.ecommerce.aafrincosmetics.service.OrderService;
import com.ecommerce.aafrincosmetics.service.Others.EmailService;
import com.ecommerce.aafrincosmetics.service.Others.MiscService;
import com.ecommerce.aafrincosmetics.service.ShipmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CheckoutController {

    private final ShipmentService shipmentService;
    private final CartService cartService;

    private final MiscService miscService;



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
