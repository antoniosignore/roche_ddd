package com.roche.ddd.domain.repository;

import com.roche.ddd.domain.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {

    List<Product> findAll();

    Iterable<Product> findAllDeleted();

    Optional<Product> findBySku(String sku);

    Product save(Product product);

    Product delete(Product product);

    void fulldelete(Product product);

    long count();
}
