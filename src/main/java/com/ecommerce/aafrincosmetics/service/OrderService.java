package com.ecommerce.aafrincosmetics.service;

import com.ecommerce.aafrincosmetics.dto.response.ProductsResponseDto;
import com.ecommerce.aafrincosmetics.entity.Order;

public interface OrderService {

    //Save order to table
    Order saveOrderToTable(Integer shipmentId, String paymentMethod);

    Order directOrder(Integer id);
}
