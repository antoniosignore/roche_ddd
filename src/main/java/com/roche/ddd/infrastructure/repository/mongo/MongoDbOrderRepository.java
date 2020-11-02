package com.roche.ddd.infrastructure.repository.mongo;

import com.roche.ddd.domain.Order;
import com.roche.ddd.domain.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class MongoDbOrderRepository implements OrderRepository {

    private final SpringDataMongoOrderRepository orderRepository;
    private final MongoTemplate mongoTemplate;

    @Autowired
    public MongoDbOrderRepository(final SpringDataMongoOrderRepository orderRepository, MongoTemplate mongoTemplate) {
        this.orderRepository = orderRepository;
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Optional<Order> findById(final UUID id) {
        return orderRepository.findById(id);
    }

    @Override
    public void save(final Order order) {
        orderRepository.save(order);
    }

    @Override
    public List<Order> getOrdersInRange(Date start, Date end) {
        Query query = new Query();
        query.addCriteria(Criteria.where("createdAt").gte(start).lt(end));
        List<Order> users = mongoTemplate.find(query, Order.class);
        return users;
    }
}
