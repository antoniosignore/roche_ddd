package com.roche.ddd.application.rest;

import com.roche.ddd.domain.Product;
import com.roche.ddd.domain.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Date;

@RestController
public class ProductController implements ProductApi {

    private ProductService productService;

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public Iterable<Product> list(Model model) {
        Iterable<Product> productList = productService.listAllProducts();
        return productList;
    }

    @Override
    public ResponseEntity<Product> showProduct(@PathVariable String sku, Model model) {
        Product product = productService.getProductBySKU(sku);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Product> addProduct(@RequestParam String name,
        @RequestParam BigDecimal price,
        @RequestParam String sku) {
        Product product = new Product();
        product.setName(name);
        product.setPrice(price);
        product.setSku(sku);
        product.setCreatedAt(new Date());
        product.setUpdatedAt(new Date());
        productService.saveProduct(product);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Product> updateProduct(@PathVariable String sku,
        @RequestParam String name, @RequestParam BigDecimal price) {
        Product product = productService.getProductBySKU(sku);
        product.setName(name);
        product.setPrice(price);
        product.setUpdatedAt(new Date());
        productService.saveProduct(product);
        return new ResponseEntity<>(product,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> delete(@PathVariable String sku) {
        Product product = productService.getProductBySKU(sku);
        product.setActive(false);
        productService.saveProduct(product);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
