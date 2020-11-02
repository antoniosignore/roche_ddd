package com.roche.ddd.domain.repository;

import com.roche.ddd.domain.Order;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderRepository {
    Optional<Order> findById(UUID id);

    void save(Order order);

    List<Order> getOrdersInRange(Date start, Date end);
}
