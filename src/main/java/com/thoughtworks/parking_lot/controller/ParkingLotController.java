package com.thoughtworks.parking_lot.controller;

import com.thoughtworks.parking_lot.core.ParkingLot;
import com.thoughtworks.parking_lot.service.ParkingLotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/parking-lot")
public class ParkingLotController {

    @Autowired
    ParkingLotService parkingLotService;

    @PostMapping(produces = {"application/json"})
    @ResponseStatus(code = HttpStatus.CREATED)
    public ParkingLot addParkingLot(@RequestBody ParkingLot parkingLot) {
        return parkingLotService.addParkingLot(parkingLot);
    }
}
