package com.ecommerce.aafrincosmetics.service;

import com.ecommerce.aafrincosmetics.dto.request.ShipmentRequestDto;
import com.ecommerce.aafrincosmetics.dto.response.ShipmentResponseDto;

import java.util.List;

public interface ShipmentService {
    //Creating the shipment details
    ShipmentResponseDto saveShipmentToTable(ShipmentRequestDto dto);

    //GetAllShipment
    List<ShipmentResponseDto> getAllShipmentDetails();

    //Update the shipment
    ShipmentResponseDto updateShipmentDetails(Integer shipmentId, ShipmentRequestDto shipmentRequestDto);

    //Delete the shipment
    void deleteShipmentDetails(Integer shipmentId);
}
