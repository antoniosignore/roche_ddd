package com.roche.ddd.domain.service;

import com.roche.ddd.domain.Order;
import com.roche.ddd.domain.Product;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface OrderService {
    UUID createOrder(Product product, String buyerEmail);

    void addProduct(UUID id, Product product);

    void completeOrder(UUID id);

    void deleteProduct(UUID id, UUID productId);

    List<Order> getOrdersInRange(Date start, Date end);
}
