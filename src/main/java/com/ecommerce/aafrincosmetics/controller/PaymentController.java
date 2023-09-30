package com.ecommerce.aafrincosmetics.controller;


import com.ecommerce.aafrincosmetics.dto.request.Request;
import com.ecommerce.aafrincosmetics.entity.Order;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PaymentController {
    @Value("${stripe.api.publicKey}")
    private String publicKey;
    @GetMapping("/stripe-payment")
    public String home(Model model, @ModelAttribute("createdOrder")Order createdOrder){
        model.addAttribute("request", new Request(createdOrder));
        return "/stripe/index";
    }
    @PostMapping("/stripe-payment")
    public String showCard(@ModelAttribute Request request,
                           BindingResult bindingResult,
                           Model model){
        if (bindingResult.hasErrors()){
            System.out.println("Binding Errors");
            return "/stripe/index";
        }
        model.addAttribute("publicKey", publicKey);
        model.addAttribute("amount", request.getAmount());
        model.addAttribute("email", request.getEmail());
        model.addAttribute("productName", request.getProductName());
        return "/stripe/checkout";
    }
}
