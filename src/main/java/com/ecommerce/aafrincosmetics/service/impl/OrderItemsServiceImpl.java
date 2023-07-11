package com.ecommerce.aafrincosmetics.service.impl;

import com.ecommerce.aafrincosmetics.dto.response.CartResponseDto;
import com.ecommerce.aafrincosmetics.entity.Cart;
import com.ecommerce.aafrincosmetics.entity.Order;
import com.ecommerce.aafrincosmetics.entity.OrderItems;
import com.ecommerce.aafrincosmetics.entity.Products;
import com.ecommerce.aafrincosmetics.repo.CartRepo;
import com.ecommerce.aafrincosmetics.repo.OrderItemsRepo;
import com.ecommerce.aafrincosmetics.repo.ProductsRepo;
import com.ecommerce.aafrincosmetics.service.CartService;
import com.ecommerce.aafrincosmetics.service.OrderItemsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class OrderItemsServiceImpl implements OrderItemsService {

    private final OrderItemsRepo orderItemsRepo;
    private final CartService cartService;
    private final ProductsRepo productsRepo;



    @Override
    public List<OrderItems> generateOrderItems(Order order) {
        List<OrderItems> returnList = new ArrayList<>();

//        Getting all the items in the cart
        List<CartResponseDto> foundCartItems = cartService.getAllCartItemsOfUser();

        //Save each item in the cart to the order
        for(CartResponseDto each: foundCartItems){
            OrderItems newOrderItem = new OrderItems();
            newOrderItem.setPrice(each.getProducts().getPrice());
            newOrderItem.setQuantity(each.getQuantity());
            newOrderItem.setProduct(each.getProducts());
            newOrderItem.setOrder(order);
            //Save the item to Order
            returnList.add(orderItemsRepo.save(newOrderItem));
            //Then delete it from cart
            cartService.deleteItemInCart(each.getId());
            //Then modify the stock amount of that product
            Products foundProduct = productsRepo.findById(each.getProducts().getId()).get();
            foundProduct.setStock(foundProduct.getStock() - each.getQuantity());
            productsRepo.save(foundProduct);
        }
        return returnList;
    }

//    @Override
//    public List<OrderItems> getAllOrderItemsOfUser(Integer orderId) {
//        return null;
//    }
}
