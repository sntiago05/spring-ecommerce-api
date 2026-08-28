package com.sntiago05.ecommerceapi.product.repository;

import com.sntiago05.ecommerceapi.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByName(String name);
}
