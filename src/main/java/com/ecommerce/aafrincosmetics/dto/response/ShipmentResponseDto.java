package com.ecommerce.aafrincosmetics.dto.response;


import com.ecommerce.aafrincosmetics.entity.Shipment;
import com.ecommerce.aafrincosmetics.service.ShipmentService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.tool.schema.internal.SchemaCreatorImpl;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShipmentResponseDto {
    private Integer id;

    private String address;

    private String city;

    private String state;

    private String phone;

    private String phone1;

    public ShipmentResponseDto(Shipment shipment){
        this.id = shipment.getId();
        this.address = shipment.getAddress();
        this.city = shipment.getCity();
        this.state = shipment.getState();
        this.phone = shipment.getPhone();
        this.phone1 = shipment.getPhone1();
    }
}
