package com.roche.ddd.infrastructure.repository.jpa;

import com.roche.ddd.domain.Product;
import com.roche.ddd.domain.repository.ProductRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class JpaDbProductRepository implements ProductRepository {

    private final SpringDataJpaProductRepository springDataJpaProductRepository;

    public JpaDbProductRepository(SpringDataJpaProductRepository springDataJpaProductRepository) {
        this.springDataJpaProductRepository = springDataJpaProductRepository;
    }

    @Override
    public List<Product> findAll() {
        return springDataJpaProductRepository.findAll();
    }

    @Override
    public Iterable<Product> findAllDeleted() {
        return springDataJpaProductRepository.findAllDeleted();
    }

    @Override
    public Optional<Product> findBySku(String sku) {
        return springDataJpaProductRepository.findBySku(sku);
    }

    @Override
    public Product save(Product product) {
        return springDataJpaProductRepository.save(product);
    }

    public Product delete(Product product) {
        product.setActive(false);
        return springDataJpaProductRepository.save(product);
    }

    public void fulldelete(Product product) {
        springDataJpaProductRepository.delete(product);
    }

    public long count() {
        return springDataJpaProductRepository.count();
    }
}
