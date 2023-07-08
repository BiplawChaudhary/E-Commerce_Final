package com.ecommerce.aafrincosmetics.dto.response;


import com.ecommerce.aafrincosmetics.entity.Order;
import com.ecommerce.aafrincosmetics.entity.Shipment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.context.annotation.Primary;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponseDto {
    private Integer id;

    private String orderNo;

    private String status;

    private String paymentMethod;

    private Shipment shipment;

    public OrderResponseDto(Order order){
        this.id = order.getId();
        this.orderNo = order.getOrderNo();
        this.status = order.getStatus();
        this.paymentMethod = order.getPaymentMethod();
        this.shipment = order.getShipment();
    }

}
