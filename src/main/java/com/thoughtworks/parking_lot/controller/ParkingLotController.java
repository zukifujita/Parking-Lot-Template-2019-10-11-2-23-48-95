package com.thoughtworks.parking_lot.controller;

import com.thoughtworks.parking_lot.core.ParkingLot;
import com.thoughtworks.parking_lot.service.ParkingLotService;
import javassist.NotFoundException;
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

    @DeleteMapping(path = {"/{id}"})
    @ResponseStatus(code = HttpStatus.OK)
    public ParkingLot deleteParking(@PathVariable Integer id) throws NotFoundException {
        return parkingLotService.deleteParkingLot(id);
    }

    @GetMapping(produces = {"application/json"})
    @ResponseStatus(code = HttpStatus.OK)
    public Iterable<ParkingLot> showParkingLot(@RequestParam(required = false) Integer page,
                                               @RequestParam(required = false) Integer pageSize) {
        return parkingLotService.showAllParkingLotsByPage(page, pageSize);
    }
}
