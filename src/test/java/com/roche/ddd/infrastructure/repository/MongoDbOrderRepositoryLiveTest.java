package com.roche.ddd.infrastructure.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import org.junit.Ignore;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.roche.ddd.domain.Order;
import com.roche.ddd.domain.Product;
import com.roche.ddd.domain.repository.OrderRepository;
import com.roche.ddd.infrastructure.repository.mongo.SpringDataMongoOrderRepository;

/*
 To run this test we need to run the databases first.
 A dedicated docker-compose.yml file is located under the resources directory.
 We can run it by simple executing `docker-compose up`.
 */
@SpringJUnitConfig
@SpringBootTest
@TestPropertySource("classpath:ddd-layers-test.properties")
class MongoDbOrderRepositoryLiveTest {

    @Autowired
    private SpringDataMongoOrderRepository mongoOrderRepository;

    @Autowired
    private OrderRepository orderRepository;

    @AfterEach
    void cleanUp() {
        mongoOrderRepository.deleteAll();
    }

    @Ignore
    @Test
    void shouldFindById_thenReturnOrder() {

        // given
        final UUID id = UUID.randomUUID();
        final Order order = createOrder(id);

        // when
        orderRepository.save(order);

        final Optional<Order> result = orderRepository.findById(id);

        assertEquals(order, result.get());
    }

    private Order createOrder(UUID id) {
        Product product = new Product(UUID.randomUUID(), BigDecimal.TEN, "product", "SKU", new Date(), new Date(), true);
        return new Order(id, product,"me@gmail.com");
    }
}