package com.roche.ddd.domain;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

public class OrderProvider {
    public static Order getCreatedOrder() {
        return new Order(UUID.randomUUID(), new Product(UUID.randomUUID(), BigDecimal.TEN, "product", "SKU", new Date(), new Date(), true), "me@gmail.com");
    }

    public static Order getCompletedOrder() {
        final Order order = getCreatedOrder();
        order.complete();
        return order;
    }
}
