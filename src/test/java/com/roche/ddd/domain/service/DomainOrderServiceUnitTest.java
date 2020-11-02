package com.roche.ddd.domain.service;

import com.roche.ddd.domain.Order;
import com.roche.ddd.domain.OrderProvider;
import com.roche.ddd.domain.Product;
import com.roche.ddd.domain.repository.OrderRepository;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class DomainOrderServiceUnitTest {

    private OrderRepository orderRepository;
    private DomainOrderService domainOrderService;

    @BeforeEach
    void setUp() {
        orderRepository = mock(OrderRepository.class);
        domainOrderService = new DomainOrderService(orderRepository);
    }

    @Test
    void shouldCreateOrder_thenSaveIt() {
        final Product product = new Product(UUID.randomUUID(), BigDecimal.TEN, "productName","SKU", new Date(), new Date(),true);

        final UUID id = domainOrderService.createOrder(product,"me@gmail.com");

        verify(orderRepository).save(any(Order.class));
        assertNotNull(id);

        List<Order> ordersInRange = domainOrderService.getOrdersInRange(new Date(), new Date());

        Assert.assertEquals(0,ordersInRange.size());

        Calendar cal = Calendar.getInstance();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println("Today's date is "+dateFormat.format(cal.getTime()));
        cal.add(Calendar.DATE, -1);

        Date yesterday = cal.getTime();
        System.out.println("Yesterday's date was "+dateFormat.format(yesterday));


        System.out.println("Today's date is "+dateFormat.format(cal.getTime()));
        cal.add(Calendar.DATE, +1);
        Date tomorrow = cal.getTime();
        System.out.println("Tomorrow's date is "+dateFormat.format(tomorrow));

        ordersInRange = domainOrderService.getOrdersInRange(yesterday, tomorrow);

        // TODO - fix the Mongo query in betweed dates
        //Assert.assertEquals(1,ordersInRange.size());
    }

    @Test
    void shouldAddProduct_thenSaveOrder() {
        final Order order = spy(OrderProvider.getCreatedOrder());
        final Product product = new Product(UUID.randomUUID(), BigDecimal.TEN, "test","SKU", new Date(), new Date(), true);
        when(orderRepository.findById(order.getId())).thenReturn(Optional.of(order));

        domainOrderService.addProduct(order.getId(), product);

        verify(orderRepository).save(order);
        verify(order).addOrder(product);
    }

    @Test
    void shouldAddProduct_thenThrowException() {
        final Product product = new Product(UUID.randomUUID(), BigDecimal.TEN, "test","SKU", new Date(),new Date(), true);
        final UUID id = UUID.randomUUID();
        when(orderRepository.findById(id)).thenReturn(Optional.empty());

        final Executable executable = () -> domainOrderService.addProduct(id, product);

        verify(orderRepository, times(0)).save(any(Order.class));
        assertThrows(RuntimeException.class, executable);
    }

    @Test
    void shouldCompleteOrder_thenSaveIt() {
        final Order order = spy(OrderProvider.getCreatedOrder());
        when(orderRepository.findById(order.getId())).thenReturn(Optional.of(order));

        domainOrderService.completeOrder(order.getId());

        verify(orderRepository).save(any(Order.class));
        verify(order).complete();
    }

    @Test
    void shouldDeleteProduct_thenSaveOrder() {
        final Order order = spy(OrderProvider.getCreatedOrder());
        final UUID productId = order
          .getOrderItems()
          .get(0)
          .getProductId();

        when(orderRepository.findById(order.getId())).thenReturn(Optional.of(order));

        domainOrderService.deleteProduct(order.getId(), productId);

        verify(orderRepository).save(order);
        verify(order).removeOrder(productId);
    }
}