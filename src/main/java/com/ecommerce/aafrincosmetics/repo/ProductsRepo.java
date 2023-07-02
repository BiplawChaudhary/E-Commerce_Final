package com.ecommerce.aafrincosmetics.repo;

import com.ecommerce.aafrincosmetics.dto.response.ProductsResponseDto;
import com.ecommerce.aafrincosmetics.entity.Category;
import com.ecommerce.aafrincosmetics.entity.Products;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductsRepo extends JpaRepository<Products, Integer> {

//    List<ProductsResponseDto> findByCategory_CategoryName(String name);
    List<ProductsResponseDto> findByCategory(Category category);
}
