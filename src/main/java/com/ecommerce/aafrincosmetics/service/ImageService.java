package com.ecommerce.aafrincosmetics.service;


import com.ecommerce.aafrincosmetics.dto.request.ImageRequestDto;
import com.ecommerce.aafrincosmetics.dto.response.ImageResponseDto;

import java.util.List;

public interface ImageService {
    //Save the image
    ImageResponseDto saveImageToTable(ImageRequestDto dto);

    //Get Image By Id
    ImageResponseDto getImageById(Integer id);

    //Get All Images
    List<ImageResponseDto> getAllImages();

    //Update the images
    ImageResponseDto updateTheImage(Integer id, ImageRequestDto dto);

    //Delete the image
    boolean deleteTheImage(Integer id);
}
