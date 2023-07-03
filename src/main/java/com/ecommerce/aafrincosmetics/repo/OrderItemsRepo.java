package com.ecommerce.aafrincosmetics.repo;

import com.ecommerce.aafrincosmetics.entity.OrderItems;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemsRepo extends JpaRepository<OrderItems, Integer> {
}
