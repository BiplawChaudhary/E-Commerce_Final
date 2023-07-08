package com.ecommerce.aafrincosmetics.controller;

import com.ecommerce.aafrincosmetics.entity.Order;
import com.ecommerce.aafrincosmetics.entity.OrderItems;
import com.ecommerce.aafrincosmetics.service.OrderItemsService;
import com.ecommerce.aafrincosmetics.service.OrderService;
import com.ecommerce.aafrincosmetics.service.Others.EmailService;
import com.ecommerce.aafrincosmetics.service.Others.MiscService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final OrderItemsService orderItemsService;

    private final EmailService emailService;
    private final MiscService miscService;



    @GetMapping("/set-order-items")
    public String getOrderItems(@ModelAttribute("createdOrder") Order createdOrder){
        List<OrderItems> orderItems = orderItemsService.generateOrderItems(createdOrder);

        //--Send the email to the user
        String subject = "Order Placed Successfully";

        String message = "Dear User, \n" +
                "Your order has been placed successfully." +
                "Your order number is \" " + createdOrder.getOrderNo() + "\" ." +
                "The items you ordered are: \n\n"+
                "Item Name | \tPrice | \tUnits | \tTotal| \n";

        for(OrderItems each: orderItems){
            message += each.getProduct().getProductName()+" | \t";
            message += each.getPrice()+" | \t";
            message += each.getQuantity() +" | \t";
            message += each.getPrice() * each.getQuantity() + " | \t";
            message += "\n\n";
        }
        System.out.println("MEssage: \n "+ message);// #Debug
        emailService.sendEmail(miscService.getLoggedInUser().getEmail(), subject, message);
        System.out.println("Order Done");
        return "redirect:/";
    }

    @PostMapping("/create-order")
    public String createOrderForUser(RedirectAttributes redirectAttributes,
                                     @RequestParam("selectedDetails") String[] selectedDetails,
                                     @RequestParam("paymentMethod") String paymentMethod){

        Order createdOrder = orderService.saveOrderToTable(Integer.valueOf(selectedDetails[0]),paymentMethod);
        redirectAttributes.addAttribute("createdOrder", createdOrder);
        return "redirect:/set-order-items";

    }
}
