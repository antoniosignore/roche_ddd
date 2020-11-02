package com.roche.ddd.domain.service;

import com.roche.ddd.domain.Product;
import com.roche.ddd.domain.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DomainProductService implements ProductService {

    private ProductRepository productRepository;

    @Autowired
    public void setProductRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Iterable<Product> listAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Iterable<Product> listAllDeletedProducts() {
        return productRepository.findAllDeleted();
    }

    @Override
    public Product getProductBySKU(String sku) {
        return productRepository.findBySku(sku).orElse(null);
    }

    @Override
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }
}
