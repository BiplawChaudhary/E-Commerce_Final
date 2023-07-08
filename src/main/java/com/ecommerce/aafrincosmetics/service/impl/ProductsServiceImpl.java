package com.ecommerce.aafrincosmetics.service.impl;

import com.ecommerce.aafrincosmetics.dto.request.ProductsRequestDto;
import com.ecommerce.aafrincosmetics.dto.response.ProductsResponseDto;
import com.ecommerce.aafrincosmetics.entity.Category;
import com.ecommerce.aafrincosmetics.entity.Products;
import com.ecommerce.aafrincosmetics.repo.CategoryRepo;
import com.ecommerce.aafrincosmetics.repo.ImagesRepo;
import com.ecommerce.aafrincosmetics.repo.ProductsRepo;
import com.ecommerce.aafrincosmetics.service.ProductsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ProductsServiceImpl implements ProductsService {
    private final ProductsRepo productsRepo;
    private final CategoryRepo categoryRepo;
    @Override
    public ProductsResponseDto saveProductToTable(ProductsRequestDto dto) {
        Products newProduct = new Products();

        newProduct.setProductName(dto.getProductName());
        newProduct.setDescription(dto.getDescription());
        newProduct.setStock(dto.getStock());
        newProduct.setPrice(dto.getPrice());
        newProduct.setCategory(dto.getCategory());
        newProduct.setImages(dto.getImages());
        newProduct.setManufacturer(dto.getManufacturer());

        return new ProductsResponseDto(productsRepo.save(newProduct));
    }

    @Override
    public ProductsResponseDto getProductById(Integer id) {
        return new ProductsResponseDto(productsRepo.findById(id).get());
    }

    @Override
    public List<ProductsResponseDto> getAllProducts() {
        List<ProductsResponseDto> returnList = new ArrayList<>();

        List<Products> allProduct = productsRepo.findAll();

        for(Products each: allProduct){
            returnList.add(new ProductsResponseDto(each));
        }
        return returnList;
    }

    @Override
    public ProductsResponseDto updateTheProduct(Integer id, ProductsRequestDto dto) {
        Products foundProduct = productsRepo.findById(id).get();

        foundProduct.setProductName(dto.getProductName());
        foundProduct.setDescription(dto.getDescription());
        foundProduct.setStock(dto.getStock());
        foundProduct.setPrice(dto.getPrice());
        foundProduct.setCategory(dto.getCategory());
        foundProduct.setImages(dto.getImages());
        foundProduct.setManufacturer(dto.getManufacturer());

        return new ProductsResponseDto(productsRepo.save(foundProduct));
    }

    @Override
    public boolean deleteTheProduct(Integer id) {
        productsRepo.deleteById(id);
        return true;
    }


    //Getting the Product By category
    @Override
    public Map<String, List<ProductsResponseDto>> getProductByCategory() {
        //Map to store products by category
        Map<String, List<ProductsResponseDto>> productByCategory = new HashMap<>();

        //Retrieving all category
        List<Category> allCategory = categoryRepo.findAll();

//        Populating the map with product for each category
        for(Category category: allCategory){
            List<ProductsResponseDto> products = productsRepo.findByCategory(category);
            productByCategory.put(category.getCategoryName(), products);
        }

        return productByCategory;
//        List<ProductsResponseDto> allProducts= getAllProducts();
//
//        System.out.println("\nON calling product with category, it's size: "+ allProducts.size());
//
//        for(ProductsResponseDto each: allProducts){
//            String categoryName = each.getCategory().getCategoryName();
//            productByCategory.computeIfAbsent(categoryName,k -> new ArrayList<>()).add(each);
//        }
//        return productByCategory;





    }
}
