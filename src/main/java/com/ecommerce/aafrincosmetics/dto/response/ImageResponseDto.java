package com.ecommerce.aafrincosmetics.dto.response;

import com.ecommerce.aafrincosmetics.entity.Images;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ImageResponseDto {
    private Integer id;

    private String path;

    private String imageName;

    private Integer size;

    public ImageResponseDto(Images img){
        this.id = img.getId();
        this.path = img.getPath();
        this.imageName = img.getImageName();
        this.size = img.getSize();
    }
}
