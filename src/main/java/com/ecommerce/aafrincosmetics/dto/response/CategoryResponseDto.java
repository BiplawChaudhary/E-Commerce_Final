package com.ecommerce.aafrincosmetics.dto.response;


import com.ecommerce.aafrincosmetics.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryResponseDto {
    private Integer id;
    private String categoryName;

    public CategoryResponseDto(Category category){
        this.id = category.getId();
        this.categoryName = category.getCategoryName();
    }
}
