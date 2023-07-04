package com.ecommerce.aafrincosmetics.controller;

import com.ecommerce.aafrincosmetics.dto.request.CartRequestDto;
import com.ecommerce.aafrincosmetics.dto.request.ShipmentRequestDto;
import com.ecommerce.aafrincosmetics.dto.response.CartResponseDto;
import com.ecommerce.aafrincosmetics.entity.Category;
import com.ecommerce.aafrincosmetics.entity.Order;
import com.ecommerce.aafrincosmetics.entity.OrderItems;
import com.ecommerce.aafrincosmetics.entity.Products;
import com.ecommerce.aafrincosmetics.repo.CategoryRepo;
import com.ecommerce.aafrincosmetics.repo.ProductsRepo;
import com.ecommerce.aafrincosmetics.service.*;
import com.ecommerce.aafrincosmetics.service.Others.EmailService;
import com.ecommerce.aafrincosmetics.service.Others.MiscService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.http11.filters.SavedRequestInputFilter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    private final OrderService orderService;
    private final OrderItemsService orderItemsService;

    private final EmailService emailService;


    //For search
    private final ProductsRepo productsRepo;
    private final CategoryRepo categoryRepo;


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

    //Search Box Functionality
    @GetMapping("/search")
    public String searchForValue(@RequestParam(value = "searchCategory", required = false) String searchCategory,
                                 @RequestParam("searchProduct") String searchProduct,
                                 Model model){



        //If the search category is null ,then search in the whole products
        if("noCategory".equals(searchCategory)){
            System.out.println("Only no categgory called\n");
            //Only one value called
            List<Products> foundProducts  = productsRepo.findByProductNameContainingIgnoreCase(searchProduct);
            model.addAttribute("foundProducts",foundProducts );
            //Display each product
//            for(Products each: foundProducts){
//                System.out.println(each.getProductName());
//            }


        } else if (searchCategory != "noCategory" && searchProduct != null) {
            System.out.println("Both not null called."); //Debug
            Category foundCategory = categoryRepo.findByCategoryName(searchCategory);

            List<Products> foundProducts = productsRepo.findByProductNameContainingIgnoreCaseAndCategoryId(searchProduct,foundCategory.getId());

            model.addAttribute("foundProducts", foundProducts);
            //Display each product
//            for(Products each: foundProducts){
//                System.out.println(each.getProductName());
//            }
        }
        return "demo/searchResult";
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

    @GetMapping("/set-order-items")
    public String getOrderItems(@ModelAttribute("createdOrder") Order createdOrder){
        List<OrderItems> orderItems = orderItemsService.generateOrderItems(createdOrder);

        //--Send the email to the user
        String subject = "Order Placed Successfully";

        String message = "Dear User, \n" +
                "Your order has been placed successfully." +
                "Your order number is " + createdOrder.getOrderNo() + "." +
                "The items you ordered are: \n\n";

        for(OrderItems each: orderItems){
            message += each.getProduct().getProductName();
            message += each.getPrice();
            message += "\n\n";
        }

        emailService.sendEmail(miscService.getLoggedInUser().getEmail(), subject, message);
        System.out.println("Order Done");
        return "redirect:/";
    }

    @PostMapping("/create-order")
    public String createOrderForUser(RedirectAttributes redirectAttributes,
                                     @RequestParam("selectedDetails") String[] selectedDetails){

        Order createdOrder = orderService.saveOrderToTable(Integer.valueOf(selectedDetails[0]));
        redirectAttributes.addAttribute("createdOrder", createdOrder);
        return "redirect:/set-order-items";

    }





}
