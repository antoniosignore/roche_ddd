package com.roche.ddd.application.rest;

import com.roche.ddd.domain.Product;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RequestMapping("/products")
@Api(value = "Orders api", tags = {"Products API"})
public interface ProductApi {

    @ApiOperation(value = "View a list of available products", response = Iterable.class)
    @GetMapping(value = "/", produces = "application/json")
    Iterable<Product> list(Model model);

    @ApiOperation(value = "Search a product by SKU", response = Product.class)
    @GetMapping(value = "/show/{sku}",
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Product> showProduct(@PathVariable String sku, Model model);

    @ApiOperation(value = "Add a product")
    @PostMapping(value = "/add",
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Product> addProduct(
        @RequestParam String name,
        @RequestParam BigDecimal price,
        @RequestParam String sku);

    @ApiOperation(value = "Update a product")
    @PutMapping(value = "/update/{sku}",
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Product> updateProduct(@PathVariable String sku, @RequestParam String name, @RequestParam BigDecimal price);

    @ApiOperation(value = "Soft delete a product. Active = false")
    @DeleteMapping(value = "/delete/{sku}",
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Void> delete(@PathVariable String sku);
}
