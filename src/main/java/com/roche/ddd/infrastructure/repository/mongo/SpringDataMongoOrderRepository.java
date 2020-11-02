package com.roche.ddd.infrastructure.repository.mongo;

import com.roche.ddd.domain.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Repository
public interface SpringDataMongoOrderRepository extends MongoRepository<Order, UUID> {
    List<Order> getAllByCreatedAtBetween(Date start, Date end);
}
