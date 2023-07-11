package com.ecommerce.aafrincosmetics.controller;


import com.ecommerce.aafrincosmetics.dto.request.ShipmentRequestDto;
import com.ecommerce.aafrincosmetics.dto.response.CartResponseDto;
import com.ecommerce.aafrincosmetics.entity.Order;
import com.ecommerce.aafrincosmetics.entity.OrderItems;
import com.ecommerce.aafrincosmetics.service.*;
import com.ecommerce.aafrincosmetics.service.Others.EmailService;
import com.ecommerce.aafrincosmetics.service.Others.MiscService;
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
    private final CategoryService categoryService;
    private final MiscService miscService;



    //Mapping for checkout
    @GetMapping("/checkout")
    public String getCheckoutPage(Model model){
        if(miscService.isUserLoggedIn()){
            model.addAttribute("shipmentDetails", shipmentService.getAllShipmentDetails());

            Integer total= cartService.getTotalCartValueOfUser();
            model.addAttribute("cartItems", cartService.getAllCartItemsOfUser());
            model.addAttribute("cartValue", cartService.getTotalCartValueOfUser());
//            model.addAttribute("cartItems",allCartItems);
            model.addAttribute("totalPrice", total);
            model.addAttribute("allCategory", categoryService.getAllCategory());
            return "main/checkout";
        }else{
            return "redirect:/login";
        }
    }

    @GetMapping("/get-add-address-form")
    public String getAddAddressForm(Model model){
            model.addAttribute("detail", new ShipmentRequestDto());
        model.addAttribute("cartValue", cartService.getTotalCartValueOfUser());
        model.addAttribute("allCategory", categoryService.getAllCategory());
        model.addAttribute("shipmentDetails", shipmentService.getAllShipmentDetails());
            return "main/detailsForm";

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
        return "redirect:/get-add-address-form";
    }

}
