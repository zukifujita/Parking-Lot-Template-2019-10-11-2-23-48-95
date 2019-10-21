package com.thoughtworks.parking_lot.service;

import com.thoughtworks.parking_lot.core.Orders;
import com.thoughtworks.parking_lot.core.ParkingLot;
import com.thoughtworks.parking_lot.repository.OrderRepository;
import com.thoughtworks.parking_lot.repository.ParkingLotRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.NotSupportedException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


@Service
public class OrderService {

    private static final String NOT_ENOUGH_SPACE = "Not enough space!";
    private static final String ORDER_ALREADY_CLOSED = "Order already closed.";
    private static final String ORDER_NOT_FOUND = "Order not found.";

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ParkingLotRepository parkingLotRepository;

    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    public Orders addOrder(Orders orders, String name) throws NotSupportedException {
        ParkingLot parkingLot = parkingLotRepository.findByName(name);
        if (parkingLot.getCapacity() > 0) {
            Date date = new Date();
            parkingLot.setCapacity(parkingLot.getCapacity() - 1);
            orders.setParkingLotName(parkingLot.getName());
            orders.setCreationTime(dateFormat.format(date));
            orders.setOrderStatus("open");
            return orderRepository.save(orders);
        }

        throw new NotSupportedException(NOT_ENOUGH_SPACE);
    }

    public Iterable<Orders> showAllOrder(Integer page, Integer pageSize) {
        if (page != null && pageSize != null) {
            return orderRepository.findAll(PageRequest.of(page, pageSize));
        }

        return orderRepository.findAll();
    }

    public Orders editOrder(Long orderNum) throws NotSupportedException, NotFoundException {
        Orders orders = orderRepository.findById(orderNum).get();
        if (orders != null) {
            if (orders.getCloseTime() == null) {
                Date date = new Date();
                ParkingLot parkingLot = parkingLotRepository.findByName(orders.getParkingLotName());
                parkingLot.setCapacity(parkingLot.getCapacity() + 1);
                orders.setCloseTime(dateFormat.format(date));
                orders.setOrderStatus("closed");
                parkingLotRepository.save(parkingLot);
                return orderRepository.save(orders);
            }

            throw new NotSupportedException(ORDER_ALREADY_CLOSED);
        }
        throw new NotFoundException(ORDER_NOT_FOUND);
    }
}