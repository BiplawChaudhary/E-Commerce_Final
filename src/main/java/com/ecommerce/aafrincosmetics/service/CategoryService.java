package com.ecommerce.aafrincosmetics.service;


import com.ecommerce.aafrincosmetics.dto.request.CategoryRequestDto;
import com.ecommerce.aafrincosmetics.dto.response.CategoryResponseDto;

import java.util.List;

public interface CategoryService {

    //Save it
    CategoryResponseDto saveCategoryToTable(CategoryRequestDto dto);

    //Get Category From table By Id
    CategoryResponseDto getCategoryFromTable(Integer id);

    //Get All Category
    List<CategoryResponseDto> getAllCategory();

    //Update Category
    CategoryResponseDto updateCategory(Integer id, CategoryRequestDto dto);

    //Delete the category
    boolean deleteById(Integer id);

//    CategoryResponseDto getCategoryByName(String name);
}
