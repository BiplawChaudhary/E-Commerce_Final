package com.ecommerce.aafrincosmetics.controller;

import com.ecommerce.aafrincosmetics.dto.request.CartRequestDto;
import com.ecommerce.aafrincosmetics.dto.request.ShipmentRequestDto;
import com.ecommerce.aafrincosmetics.dto.response.CartResponseDto;
import com.ecommerce.aafrincosmetics.dto.response.ProductsResponseDto;
import com.ecommerce.aafrincosmetics.entity.*;
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
    private final CartService cartService;



    //For search
    private final ProductsRepo productsRepo;
    private final CategoryRepo categoryRepo;


    //Getting the Homepage
    @GetMapping("/")
    public String getHomePage(Model model,
                              @ModelAttribute("nullQuantityError") String nullQuantityError,
                              @ModelAttribute("duplicateError") String duplicateError) {
        model.addAttribute("allCategory", categoryService.getAllCategory());
        model.addAttribute("allProduct", productsService.getAllProducts());



        //If user is logged in add their wishlist to the index page
        if(miscService.isUserLoggedIn()){
            model.addAttribute("wishlist", wishlistService.allwishlistItemsOfUser());
            model.addAttribute("cartValue", cartService.getTotalCartValueOfUser());

        }

        //Adding the cart dto the main page
        model.addAttribute("cartdto", new CartRequestDto());
        model.addAttribute("nullQuantityError", nullQuantityError);
        model.addAttribute("duplicateError", duplicateError);


        return "main/index";
    }

    //Search Box Functionality
    @GetMapping("/search")
    public String searchForValue(@RequestParam(value = "searchCategory", required = false) String searchCategory,
                                 @RequestParam("searchProduct") String searchProduct,
                                 Model model){



        //If the search category is null ,then search in the whole products
        if("noCategory".equals(searchCategory)){

            //Only one value called
            List<Products> foundProducts  = productsRepo.findByProductNameContainingIgnoreCase(searchProduct);
            model.addAttribute("foundProducts",foundProducts );
//            //Display each product
//            for(Products each: foundProducts){
//                System.out.println(each.getProductName());
//            }

        } else if (searchCategory != "noCategory" && searchProduct != null) {

            Category foundCategory = categoryRepo.findByCategoryName(searchCategory);

            List<Products> foundProducts = productsRepo.findByProductNameContainingIgnoreCaseAndCategoryId(searchProduct,foundCategory.getId());

            model.addAttribute("foundProducts", foundProducts);
////            Display each product
//            for(Products each: foundProducts){
//                System.out.println(each.getProductName());
//            }
        }
        model.addAttribute("cartValue", cartService.getTotalCartValueOfUser());
        model.addAttribute("cartdto", new CartRequestDto());
        model.addAttribute("allCategory", categoryService.getAllCategory());

        return "main/searchResult";
    }


    //// Display the single page
    @GetMapping("/get-single-product/{product_id}")
    public String getSinglePageProduct(@PathVariable("product_id") Integer productId,
                                       Model model){
            ProductsResponseDto singleProduct = productsService.getProductById(productId);
            model.addAttribute("product", singleProduct);
            model.addAttribute("cartRequestDto", new CartRequestDto());
        model.addAttribute("cartValue", cartService.getTotalCartValueOfUser());
        model.addAttribute("allCategory", categoryService.getAllCategory());
            return "main/productPage";
    }






}
