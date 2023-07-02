package com.ecommerce.aafrincosmetics.repo;

import com.ecommerce.aafrincosmetics.dto.response.CategoryResponseDto;
import com.ecommerce.aafrincosmetics.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepo extends JpaRepository<Category, Integer> {
        Category findByCategoryName(String name);
}
