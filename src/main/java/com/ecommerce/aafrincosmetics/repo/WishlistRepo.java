package com.ecommerce.aafrincosmetics.repo;

import com.ecommerce.aafrincosmetics.entity.User;
import com.ecommerce.aafrincosmetics.entity.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WishlistRepo extends JpaRepository<Wishlist, Integer> {
    List<Wishlist> findByUser(User user);
}
