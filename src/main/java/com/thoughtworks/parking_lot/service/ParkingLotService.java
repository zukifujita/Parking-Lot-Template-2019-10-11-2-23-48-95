package com.thoughtworks.parking_lot.service;

import com.thoughtworks.parking_lot.core.ParkingLot;
import com.thoughtworks.parking_lot.repository.ParkingLotRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class ParkingLotService {

    private static final String RECORD_NOT_FOUND = "Record not found!";
    @Autowired
    ParkingLotRepository parkingLotRepository;

    public ParkingLot addParkingLot(ParkingLot parkingLot) {
        return parkingLotRepository.save(parkingLot);
    }

    public ParkingLot deleteParkingLot(String name) throws NotFoundException {
        ParkingLot parkingLotFromDb = parkingLotRepository.findById(name).orElse(null);

        if (parkingLotFromDb != null) {
            parkingLotRepository.delete(parkingLotFromDb);
        }

        throw new NotFoundException(RECORD_NOT_FOUND);
    }

    public Iterable<ParkingLot> showAllParkingLotsByPage(Integer page, Integer pageSize) {
        if (page != null && pageSize != null) {
            return parkingLotRepository.findAll(PageRequest.of(page, pageSize));
        }

        return parkingLotRepository.findAll();
    }

    public ParkingLot showParkingLotByName(String name) {
        return parkingLotRepository.findById(name).orElse(null);
    }

    public ParkingLot editParkingLot(String name, ParkingLot parkingLot) throws NotFoundException {
        ParkingLot parkingLotFromDb = parkingLotRepository.findById(name).orElse(null);

        if (parkingLotFromDb != null) {
            parkingLotFromDb.setCapacity(parkingLot.getCapacity());
            parkingLotRepository.save(parkingLotFromDb);
        }

        throw new NotFoundException(RECORD_NOT_FOUND);
    }
}
