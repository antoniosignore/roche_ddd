package com.roche.ddd.domain.service;


import com.roche.ddd.domain.Product;

public interface ProductService {
    Iterable<Product> listAllProducts();

    Iterable<Product> listAllDeletedProducts();

    Product getProductBySKU(String sku);

    Product saveProduct(Product product);

}
