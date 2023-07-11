package com.ecommerce.aafrincosmetics.service.impl;

import com.ecommerce.aafrincosmetics.dto.response.CartResponseDto;
import com.ecommerce.aafrincosmetics.dto.response.OrderResponseDto;
import com.ecommerce.aafrincosmetics.entity.Cart;
import com.ecommerce.aafrincosmetics.entity.Order;
import com.ecommerce.aafrincosmetics.repo.OrderRepo;
import com.ecommerce.aafrincosmetics.repo.ShipmentRepo;
import com.ecommerce.aafrincosmetics.service.CartService;
import com.ecommerce.aafrincosmetics.service.OrderService;
import com.ecommerce.aafrincosmetics.service.Others.MiscService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepo orderRepo;
    private final CartService cartService;
    private final MiscService miscService;
    private final ShipmentRepo shipmentRepo;


    @Override
    public Order saveOrderToTable(Integer shipmentId, String paymentMethod) {
        Order newOrder = new Order();

        //Generating a random string for order
        StringBuilder sb = new StringBuilder(10);

        for (int i = 0; i < 10; i++) {
            char randomChar = (char) ((int) (Math.random() * 94) + 33);
            sb.append(randomChar);
        }



        //Adding it as order no
        newOrder.setOrderNo(sb.toString());

        newOrder.setShipment(shipmentRepo.findById(shipmentId).get());
        newOrder.setUser(miscService.getLoggedInUser());
        newOrder.setStatus("Order Created");
        newOrder.setTotalPrice(cartService.getTotalCartValueOfUser());
        newOrder.setPaymentMethod(paymentMethod);
        return orderRepo.save(newOrder);
    }



    @Override
    public Order directOrder(Integer id) {
        return null;
    }


    //Getting all the order's of a user
    @Override
    public List<OrderResponseDto> getAllOrdersOfAUser() {
        List<OrderResponseDto> returnList = new ArrayList<>();

        List<Order> allData = orderRepo.findByUser(miscService.getLoggedInUser());

        for(Order each: allData){
            returnList.add(new OrderResponseDto(each));
        }
        return returnList;
    }


    //    Set the order status accordingly
    @Override
    public Order updateTheStatus(String newStatus, Integer id) {
        Order foundOrder = orderRepo.findById(id).get();

        foundOrder.setStatus(newStatus);

        return orderRepo.save(foundOrder);
    }

    // Get Order By Id
    @Override
    public OrderResponseDto getOrderById(Integer id) {
        return new OrderResponseDto(orderRepo.findById(id).get());
    }



}
