package com.ecommerce.aafrincosmetics.controller;

import com.ecommerce.aafrincosmetics.service.CategoryService;
import com.ecommerce.aafrincosmetics.service.ProductsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomePageController {

    private final CategoryService categoryService;
    private final ProductsService productsService;

    //Getting the Homepage
    @GetMapping("/")
    public String getHomePage(Model model) {
//        if(productsService.getProductByCategory().size() !=0){
//            model.addAttribute("allCategory",categoryService.getAllCategory());
//            model.addAttribute("allProduct", productsService.getProductByCategory());
//        }
        model.addAttribute("allCategory", categoryService.getAllCategory());
        model.addAttribute("allProduct", productsService.getAllProducts());
        return "index";
    }
}
