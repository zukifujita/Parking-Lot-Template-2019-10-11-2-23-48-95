package com.thoughtworks.parking_lot.controller;

import com.thoughtworks.parking_lot.core.ParkingLot;
import com.thoughtworks.parking_lot.service.ParkingLotService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/parking-lot")
public class ParkingLotController {

    @Autowired
    ParkingLotService parkingLotService;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(code = HttpStatus.CREATED)
    public ParkingLot addParkingLot(@RequestBody ParkingLot parkingLot) {
        return parkingLotService.addParkingLot(parkingLot);
    }

    @DeleteMapping(path = "/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(code = HttpStatus.OK)
    public ParkingLot deleteParkingLot(@PathVariable String name) throws NotFoundException {
        return parkingLotService.deleteParkingLot(name);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(code = HttpStatus.OK)
    public Iterable<ParkingLot> showParkingLot(@RequestParam(required = false) Integer page,
                                               @RequestParam(required = false) Integer pageSize) {
        return parkingLotService.showAllParkingLotsByPage(page, pageSize);
    }

    @GetMapping(path = "/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(code = HttpStatus.OK)
    public ParkingLot showParkingLotByName(@PathVariable String name) {
        return parkingLotService.showParkingLotByName(name);
    }

    @PatchMapping(path = "/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(code = HttpStatus.OK)
    public ParkingLot editParkingLot(@PathVariable String name, @RequestBody ParkingLot parkingLot)
            throws NotFoundException {
        return parkingLotService.editParkingLot(name, parkingLot);
    }
}
