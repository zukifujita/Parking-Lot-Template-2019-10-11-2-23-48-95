package com.thoughtworks.parking_lot.controller;

import com.thoughtworks.parking_lot.core.Orders;
import com.thoughtworks.parking_lot.service.OrderService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.transaction.NotSupportedException;

@RestController
@RequestMapping("/parking-lot/{name}/orders")
public class OrderController {

    @Autowired
    OrderService orderService;

    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public Iterable<Orders> showAllOrder(@RequestParam(required = false) Integer page,
                                         @RequestParam(required = false) Integer pageSize) {
        return orderService.showAllOrder(page, pageSize);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(code = HttpStatus.CREATED)
    public Orders addOrder(@RequestBody Orders orders, @PathVariable String name) throws NotSupportedException {
        return orderService.addOrder(orders, name);
    }

    @PatchMapping(path = "/{orderNum}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(code = HttpStatus.CREATED)
    public Orders editOrder(@PathVariable Long orderNum) throws NotFoundException, NotSupportedException {
        return orderService.editOrder(orderNum);
    }

}
