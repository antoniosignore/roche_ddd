package com.roche.ddd.infrastructure.repository;

import com.roche.ddd.domain.Product;
import com.roche.ddd.infrastructure.repository.jpa.JpaDbProductRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Iterator;

import static org.junit.Assert.*;

@SpringJUnitConfig
@SpringBootTest
@TestPropertySource("classpath:ddd-layers-test.properties")
public class JpaDbProductRepositoryTest {

    @Autowired
    private JpaDbProductRepository productRepository;

    @Test
    public void should_save_product_and_assign_id() {
        Product product = buildProduct();
        Product saved = productRepository.save(product);

        assertNotNull(saved);
        assertNotNull(saved.getId());

        productRepository.fulldelete(product);
    }

    @Test
    public void should_fetch_product_by_sku() {

        Product product = buildProduct();
        productRepository.fulldelete(product);
        Product saved = productRepository.save(product);

        Product fetchedProduct = productRepository.findBySku(product.getSku()).orElse(null);
        //should not be null
        assertNotNull(fetchedProduct);

        //should equal
        assertEquals(saved.getSku(), fetchedProduct.getSku());
        assertEquals(saved.getName(), fetchedProduct.getName());

        productRepository.fulldelete(product);
    }


    @Test
    public void should_update_product_by_sku() {
        //update description and save
        Product product = buildProduct();
        Product saved = productRepository.save(product);

        Product fetchedProduct = productRepository.findBySku(product.getSku()).orElse(null);
        fetchedProduct.setName("New Name");
        productRepository.save(fetchedProduct);

        //get from DB, should be updated
        Product fetchedUpdatedProduct = productRepository.findBySku(fetchedProduct.getSku()).orElse(null);
        assertEquals(fetchedProduct.getName(), fetchedUpdatedProduct.getName());
        //verify count of products in DB
        long productCount = productRepository.count();
        assertEquals(productCount, 1);
        //get all products, list should only have one
        Iterable<Product> products = productRepository.findAll();
        int count = 0;
        for (Product p : products) {
            count++;
        }
        assertEquals(count, 1);
        productRepository.fulldelete(product);


    }

    private Product buildProduct() {
        Product product = new Product();
        product.setName("Shirt 1");
        product.setPrice(new BigDecimal("18.95"));
        product.setCreatedAt(new Date());
        product.setUpdatedAt(new Date());
        product.setSku("mysku");
        return product;
    }
}
