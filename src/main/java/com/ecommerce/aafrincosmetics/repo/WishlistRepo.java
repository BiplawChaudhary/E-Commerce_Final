package com.ecommerce.aafrincosmetics.repo;

import com.ecommerce.aafrincosmetics.entity.Cart;
import com.ecommerce.aafrincosmetics.entity.User;
import com.ecommerce.aafrincosmetics.entity.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WishlistRepo extends JpaRepository<Wishlist, Integer> {
    List<Wishlist> findByUser(User user);

    @Query(
            nativeQuery = true,
            value = "SELECT * FROM wishlist where customer_id=?1 and product_id=?2"
    )
    Wishlist checkIfProductExistsForUserInWishlist(Integer customerId, Integer productId);
}
