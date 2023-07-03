package com.ecommerce.aafrincosmetics.service;

import com.ecommerce.aafrincosmetics.entity.Order;
import com.ecommerce.aafrincosmetics.entity.OrderItems;

import java.util.List;

public interface OrderItemsService {

    List<OrderItems> generateOrderItems(Order order);

//    List<OrderItems> getAllOrderItemsOfUser();
}
