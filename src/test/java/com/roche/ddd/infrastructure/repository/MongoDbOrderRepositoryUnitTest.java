package com.roche.ddd.infrastructure.repository;

import com.roche.ddd.domain.Order;
import com.roche.ddd.domain.Product;
import com.roche.ddd.infrastructure.repository.mongo.MongoDbOrderRepository;
import com.roche.ddd.infrastructure.repository.mongo.SpringDataMongoOrderRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MongoDbOrderRepositoryUnitTest {

    private SpringDataMongoOrderRepository springDataOrderRepository;
    private MongoDbOrderRepository tested;

    @BeforeEach
    void setUp() {
        springDataOrderRepository = mock(SpringDataMongoOrderRepository.class);
        tested = new MongoDbOrderRepository(springDataOrderRepository, null);
    }

    @Test
    void shouldFindById_thenReturnOrder() {
        final UUID id = UUID.randomUUID();
        final Order order = createOrder(id);
        when(springDataOrderRepository.findById(id)).thenReturn(Optional.of(order));

        final Optional<Order> result = tested.findById(id);

        assertEquals(order, result.get());
    }

    @Test
    void shouldSaveOrder_viaSpringDataOrderRepository() {
        final UUID id = UUID.randomUUID();
        final Order order = createOrder(id);

        tested.save(order);

        verify(springDataOrderRepository).save(order);
    }

    private Order createOrder(UUID id) {
        Product product = new Product(UUID.randomUUID(), BigDecimal.TEN, "product", "SKU", new Date(), new Date(), true);
        return new Order(id, product,"me@gmail.com");
    }
}