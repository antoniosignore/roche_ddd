package com.roche.ddd.application.rest;

import com.roche.ddd.application.request.AddProductRequest;
import com.roche.ddd.application.request.CreateOrderRequest;
import com.roche.ddd.application.response.CreateOrderResponse;
import com.roche.ddd.domain.Order;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/orders")
@Api(value = "Orders api", tags = {"Orders API"})
public interface OrderApi {

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<CreateOrderResponse> createOrder(@RequestBody CreateOrderRequest createOrderRequest);

    @PostMapping(value = "/{id}/products", consumes = MediaType.APPLICATION_JSON_VALUE)
    void addProduct(@PathVariable UUID id, @RequestBody AddProductRequest addProductRequest);

    @DeleteMapping(value = "/{id}/products", consumes = MediaType.APPLICATION_JSON_VALUE)
    void deleteProduct(@PathVariable UUID id, @RequestParam UUID productId);

    @GetMapping(value = "/list",  produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Order> listInDateRange(
        @RequestParam(name = "start", required = false, defaultValue = "2000-01-01")
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        @ApiParam(value = "A string representation of a date in following format: yyyy-MM-dd")
            Date start,
        @RequestParam(name = "end", required = false, defaultValue = "2100-12-31")
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        @ApiParam(value = "A string representation of a date in following format: yyyy-MM-dd")
            Date end);

    @PostMapping("/{id}/complete")
    void completeOrder(@PathVariable UUID id);

}
