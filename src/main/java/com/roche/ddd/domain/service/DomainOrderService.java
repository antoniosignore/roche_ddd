package com.roche.ddd.domain.service;

import com.roche.ddd.domain.Order;
import com.roche.ddd.domain.Product;
import com.roche.ddd.domain.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class DomainOrderService implements OrderService {

    private final OrderRepository orderRepository;

    public DomainOrderService(final OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public UUID createOrder(final Product product, String buyerEmail) {
        final Order order = new Order(UUID.randomUUID(), product, buyerEmail);
        orderRepository.save(order);
        return order.getId();
    }

    @Override
    public void addProduct(final UUID id, final Product product) {
        final Order order = getOrder(id);
        order.addOrder(product);
        orderRepository.save(order);
    }

    @Override
    public void completeOrder(final UUID id) {
        final Order order = getOrder(id);
        order.complete();
        orderRepository.save(order);
    }

    @Override
    public void deleteProduct(final UUID id, final UUID productId) {
        final Order order = getOrder(id);
        order.removeOrder(productId);

        orderRepository.save(order);
    }

    @Override
    public List<Order> getOrdersInRange(Date start, Date end) {
        return orderRepository.getOrdersInRange(start, end);
    }

    private Order getOrder(UUID id) {
        return orderRepository
            .findById(id)
            .orElseThrow(() -> new RuntimeException("Order with given id doesn't exist"));
    }
}
