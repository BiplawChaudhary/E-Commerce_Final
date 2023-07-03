package com.ecommerce.aafrincosmetics.repo;

import com.ecommerce.aafrincosmetics.entity.Shipment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShipmentRepo extends JpaRepository<Shipment, Integer> {
}
