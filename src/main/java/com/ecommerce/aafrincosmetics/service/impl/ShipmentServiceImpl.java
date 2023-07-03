package com.ecommerce.aafrincosmetics.service.impl;

import com.ecommerce.aafrincosmetics.dto.request.ShipmentRequestDto;
import com.ecommerce.aafrincosmetics.dto.response.ShipmentResponseDto;
import com.ecommerce.aafrincosmetics.entity.Shipment;
import com.ecommerce.aafrincosmetics.repo.ShipmentRepo;
import com.ecommerce.aafrincosmetics.service.Others.MiscService;
import com.ecommerce.aafrincosmetics.service.ShipmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.thymeleaf.standard.expression.Each;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ShipmentServiceImpl implements ShipmentService {
    private final ShipmentRepo shipmentRepo;
    private final MiscService miscService;

    //save the shipment to table
    @Override
    public ShipmentResponseDto saveShipmentToTable(ShipmentRequestDto dto) {
        Shipment newShipment = new Shipment();

        newShipment.setAddress(dto.getAddress());
        newShipment.setCity(dto.getCity());
        newShipment.setState(dto.getState());
        newShipment.setPhone(dto.getPhone());
        newShipment.setUser(miscService.getLoggedInUser());

        return new ShipmentResponseDto(shipmentRepo.save(newShipment));
    }

//    /Get all the shipment details
    @Override
    public List<ShipmentResponseDto> getAllShipmentDetails() {
        List<ShipmentResponseDto> returnList = new ArrayList<>();

        List<Shipment> allItems = shipmentRepo.findByUser(miscService.getLoggedInUser());

        for(Shipment each: allItems){
            returnList.add(new ShipmentResponseDto(each));
        }
        return returnList;
    }

    //Update the shipping address
    @Override
    public ShipmentResponseDto updateShipmentDetails(Integer shipmentId, ShipmentRequestDto shipmentRequestDto) {
        Shipment foundDetail = shipmentRepo.findById(shipmentId).get();

        foundDetail.setAddress(shipmentRequestDto.getAddress());
        foundDetail.setCity(shipmentRequestDto.getCity());
        foundDetail.setState(shipmentRequestDto.getState());
        foundDetail.setPhone(shipmentRequestDto.getPhone());

        return new ShipmentResponseDto(foundDetail);
    }

    //Delete the shipping address
    @Override
    public void deleteShipmentDetails(Integer shipmentId) {
        shipmentRepo.deleteById(shipmentId);
    }
}
