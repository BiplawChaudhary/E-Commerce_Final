package com.ecommerce.aafrincosmetics.dto.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ImageRequestDto {
    private String path;

    private String imageName;

    private Integer size;

}
