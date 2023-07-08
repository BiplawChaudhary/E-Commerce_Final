package com.ecommerce.aafrincosmetics.controller.admincontroller;

import com.ecommerce.aafrincosmetics.dto.request.CategoryRequestDto;
import com.ecommerce.aafrincosmetics.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.Banner;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/category")
@PreAuthorize("hasRole('ADMIN')")
public class AdminCategoryController {

    private final CategoryService categoryService;


    //For Managing the Category
    @GetMapping("/home")
    public String getCategoryHomePage(Model model){
        model.addAttribute("allCategory", categoryService.getAllCategory());
        return "adminPages/categoryHome";
    }

    //Add Category Mapping
    @GetMapping("/add-category")
    public String getAddCategoryForm(Model model, @ModelAttribute("errorMsg") String errorMsg){
        model.addAttribute("dto", new CategoryRequestDto());
        model.addAttribute("error", errorMsg);
        return "adminPages/addCategory";
    }

    @PostMapping("/add-category")
    public String addCategory(RedirectAttributes redirectAttributes, @ModelAttribute("dto") CategoryRequestDto dto){
        try{
            categoryService.saveCategoryToTable(dto);
        }catch (DataIntegrityViolationException e){
            redirectAttributes.addAttribute("errorMsg", "Category Name must be unique.");
            return "redirect:/admin/category/add-category";
        }
        return "redirect:/admin/category/home";
    }

    @GetMapping("/update/{id}")
    public String getUpdateForm(Model model,@PathVariable Integer id ){
        model.addAttribute("dto", categoryService.getCategoryFromTable(id));
        return "adminPages/updateCategory";
    }

    @PostMapping("/update/{id}")
    public String updateCategory(@ModelAttribute CategoryRequestDto dto, @PathVariable Integer id){
        categoryService.updateCategory(id, dto);
        return "redirect:/admin/category/home";
    }



    //Delete Mapping
    @GetMapping("/delete/{id}")
    public String deletecategory(@PathVariable Integer id){
        categoryService.deleteById(id);
        return "redirect:/admin/category/home";
    }
}
