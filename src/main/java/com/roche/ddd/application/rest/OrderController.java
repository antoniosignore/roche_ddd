package com.roche.ddd.application.rest;

import com.roche.ddd.application.request.AddProductRequest;
import com.roche.ddd.application.request.CreateOrderRequest;
import com.roche.ddd.application.response.CreateOrderResponse;
import com.roche.ddd.domain.Order;
import com.roche.ddd.domain.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
public class OrderController implements OrderApi {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CreateOrderResponse> createOrder(@RequestBody final CreateOrderRequest createOrderRequest) {
        final UUID id = orderService.createOrder(createOrderRequest.getProduct(),"me@");
        return new ResponseEntity<>(new CreateOrderResponse(id), HttpStatus.OK);
    }

    @Override
    @PostMapping(value = "/{id}/products", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void addProduct(@PathVariable final UUID id, @RequestBody final AddProductRequest addProductRequest) {
        orderService.addProduct(id, addProductRequest.getProduct());
    }

    @Override
    @DeleteMapping(value = "/{id}/products", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void deleteProduct(@PathVariable final UUID id, @RequestParam final UUID productId) {
        orderService.deleteProduct(id, productId);
    }

    @Override
    public List<Order> listInDateRange(Date start, Date end) {
        return orderService.getOrdersInRange(start, end);
    }

    @Override
    @PostMapping("/{id}/complete")
    public void completeOrder(@PathVariable final UUID id) {
        orderService.completeOrder(id);
    }
}
