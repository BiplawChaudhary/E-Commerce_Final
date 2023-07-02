package com.ecommerce.aafrincosmetics.service.impl;

import com.ecommerce.aafrincosmetics.dto.request.ImageRequestDto;
import com.ecommerce.aafrincosmetics.dto.response.ImageResponseDto;
import com.ecommerce.aafrincosmetics.entity.Images;
import com.ecommerce.aafrincosmetics.repo.ImagesRepo;
import com.ecommerce.aafrincosmetics.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {
    private final ImagesRepo imagesRepo;
    @Override
    public ImageResponseDto saveImageToTable(ImageRequestDto dto) {
        Images newImage = new Images();

        newImage.setPath(dto.getPath());
        newImage.setImageName(dto.getImageName());
        newImage.setSize(dto.getSize());

        return new ImageResponseDto(imagesRepo.save(newImage));
    }

    @Override
    public ImageResponseDto getImageById(Integer id) {
        Images foundImage = imagesRepo.findById(id).get();
        return new ImageResponseDto(foundImage);
    }

    @Override
    public List<ImageResponseDto> getAllImages() {
        List<ImageResponseDto> returnList = new ArrayList<>();

        List<Images> allImages = imagesRepo.findAll();

        for(Images each:allImages){
            returnList.add(new ImageResponseDto(each));
        }
        return returnList;
    }

    @Override
    public ImageResponseDto updateTheImage(Integer id, ImageRequestDto dto) {
        Images foundImage = imagesRepo.findById(id).get();
        foundImage.setImageName(dto.getImageName());
        foundImage.setPath(dto.getPath());
        foundImage.setSize(dto.getSize());

        return new ImageResponseDto(imagesRepo.save(foundImage));
    }

    @Override
    public boolean deleteTheImage(Integer id) {
        imagesRepo.deleteById(id);
        return true;
    }
}
