package com.ecommerce.aafrincosmetics.service.impl;

import com.ecommerce.aafrincosmetics.dto.request.CategoryRequestDto;
import com.ecommerce.aafrincosmetics.dto.response.CategoryResponseDto;
import com.ecommerce.aafrincosmetics.dto.response.ProductsResponseDto;
import com.ecommerce.aafrincosmetics.entity.Category;
import com.ecommerce.aafrincosmetics.repo.CategoryRepo;
import com.ecommerce.aafrincosmetics.repo.ProductsRepo;
import com.ecommerce.aafrincosmetics.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepo categoryRepo;
    private final ProductsRepo productsRepo;


    @Override
    public CategoryResponseDto saveCategoryToTable(CategoryRequestDto dto) {
        Category newCategory = new Category();
        newCategory.setCategoryName(dto.getCategoryName());
        return new CategoryResponseDto(categoryRepo.save(newCategory));
    }

    @Override
    public CategoryResponseDto getCategoryFromTable(Integer id) {
        Category foundCategory = categoryRepo.findById(id).get();
        return new CategoryResponseDto(foundCategory);
    }

    @Override
    public List<CategoryResponseDto> getAllCategory() {
        List<CategoryResponseDto> allCategory = new ArrayList<>();

        List<Category> allData = categoryRepo.findAll();

        for(Category each:allData){
//            System.out.println(each); //Debug
            allCategory.add(new CategoryResponseDto(each));
        }
        return allCategory;


    }

    @Override
    public CategoryResponseDto updateCategory(Integer id, CategoryRequestDto dto) {
        Category foundCategory = categoryRepo.findById(id).get();
        foundCategory.setCategoryName(dto.getCategoryName());
        return new CategoryResponseDto(categoryRepo.save(foundCategory));
    }

    @Override
    public boolean deleteById(Integer id) {
//        Get all the products with that category
        List<ProductsResponseDto> allProductsofCategory = productsRepo.findByCategory(categoryRepo.findById(id).get());

//        Delete each of that productt
        for(ProductsResponseDto each: allProductsofCategory){
            productsRepo.deleteById(each.getId());
        }

//        Then delete the category
        categoryRepo.deleteById(id);
        return true;
    }

//    @Override
//    public CategoryResponseDto getCategoryByName(String name) {
//        return categoryRepo.findByCategoryName(name);
//
//    }
}
