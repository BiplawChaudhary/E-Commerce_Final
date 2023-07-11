package com.ecommerce.aafrincosmetics.service;

import com.ecommerce.aafrincosmetics.dto.response.OrderResponseDto;
import com.ecommerce.aafrincosmetics.dto.response.ProductsResponseDto;
import com.ecommerce.aafrincosmetics.entity.Order;

import java.util.List;

public interface OrderService {

    //Save order to table
    Order saveOrderToTable(Integer shipmentId, String paymentMethod);

    Order directOrder(Integer id);

    //Get all order items of the logged in user
    List<OrderResponseDto> getAllOrdersOfAUser();

//    Set the order status accordingly
    Order updateTheStatus(String newStatus, Integer id);

    //Get Order By id
    OrderResponseDto getOrderById(Integer id);


}

