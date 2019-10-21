package com.thoughtworks.parking_lot.repository;

import com.thoughtworks.parking_lot.core.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Long> {
}
