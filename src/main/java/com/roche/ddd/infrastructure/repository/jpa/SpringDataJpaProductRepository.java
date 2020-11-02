package com.roche.ddd.infrastructure.repository.jpa;

import com.roche.ddd.domain.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SpringDataJpaProductRepository extends CrudRepository<Product, Integer> {

    Optional<Product> findBySku(String sku);

    @Override
    @Query("select e from Product e where e.active=true")
    List<Product> findAll();

    //Look up deleted entities
    @Query("select e from Product e where e.active=false")
    List<Product> findAllDeleted();

}
