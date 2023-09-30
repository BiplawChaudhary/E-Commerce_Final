package com.ecommerce.aafrincosmetics.controller;

import com.ecommerce.aafrincosmetics.dto.response.OrderResponseDto;
import com.ecommerce.aafrincosmetics.entity.Order;
import com.ecommerce.aafrincosmetics.entity.OrderItems;
import com.ecommerce.aafrincosmetics.entity.Products;
import com.ecommerce.aafrincosmetics.service.CartService;
import com.ecommerce.aafrincosmetics.service.CategoryService;
import com.ecommerce.aafrincosmetics.service.OrderItemsService;
import com.ecommerce.aafrincosmetics.service.OrderService;
import com.ecommerce.aafrincosmetics.service.Others.EmailService;
import com.ecommerce.aafrincosmetics.service.Others.MiscService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final OrderItemsService orderItemsService;
    private final CartService cartService;
    private final EmailService emailService;
    private final MiscService miscService;
    private final CategoryService categoryService;



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
        if (paymentMethod.equals("cod")){
            return "redirect:/set-order-items";
        }
        else if(paymentMethod.equals("stripe")){
            return "redirect:/stripe-payment";
        }
        return "redirect:/";//This is an unreachable code.
//        This is just for removing the error
    }

    //Get the order's Page
    @GetMapping("/my-orders")
    public String getOrdersPage(Model model){
        model.addAttribute("allOrders", orderService.getAllOrdersOfAUser());
        model.addAttribute("total",cartService.getTotalCartValueOfUser());
        model.addAttribute("cartValue", cartService.getTotalCartValueOfUser());
        model.addAttribute("allCategory", categoryService.getAllCategory());
        return "main/order";
    }

    @GetMapping("/set-order-status/{id}/{status}")
    public String setNewOrderStatus(Model model,
                                    @PathVariable("id") Integer id,
                                    @PathVariable("status") String status){

        orderService.updateTheStatus(status,id);
        return "redirect:/my-orders";
    }

    //Get the single product's page
    @GetMapping("/get-single-order/{id}")
    public String getSingleOrderpage(Model model,
                                     @PathVariable("id") Integer id){

        OrderResponseDto foundOrder = orderService.getOrderById(id);
        model.addAttribute("foundOrder", foundOrder);
        model.addAttribute("cartValue", cartService.getTotalCartValueOfUser());
        model.addAttribute("allCategory", categoryService.getAllCategory());

//        Adding the status messge
        if(foundOrder.getStatus().trim().equals("Created")){
            model.addAttribute("statusMessage", "Your Order is placed Successfully.");
        } else if (foundOrder.getStatus().trim().equals("Picked Up")) {
            model.addAttribute("Picked Up","Your order has been picked up by the delivery team. ");
        }else if (foundOrder.getStatus().trim().equals("Sent Out")) {
            model.addAttribute("Sent Out","Your order has been sent out for delivery.\nIt should reach you soon. ");
        }else if (foundOrder.getStatus().trim().equals("Delivery")) {
            model.addAttribute("Delivery","Your order has been delivered.");
        }


        return "main/singleOrder";
    }

}
