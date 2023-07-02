package com.ecommerce.aafrincosmetics.service;

import com.ecommerce.aafrincosmetics.dto.request.ProductsRequestDto;
import com.ecommerce.aafrincosmetics.dto.response.ProductsResponseDto;

import java.util.List;
import java.util.Map;

public interface ProductsService {
    //Save the products
    ProductsResponseDto saveProductToTable(ProductsRequestDto dto);

    //Get PRoduct By id
    ProductsResponseDto getProductById(Integer id);

    //Get All Products
    List<ProductsResponseDto> getAllProducts();

    //Update the Product
    ProductsResponseDto updateTheProduct(Integer id, ProductsRequestDto dto);

    //Delete the product
    boolean deleteTheProduct(Integer id);

    Map<String, List<ProductsResponseDto>> getProductByCategory();
}
