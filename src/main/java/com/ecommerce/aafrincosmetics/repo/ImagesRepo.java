package com.ecommerce.aafrincosmetics.repo;

import com.ecommerce.aafrincosmetics.entity.Images;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImagesRepo extends JpaRepository<Images, Integer> {
    Images findByImageName(String name);
}
