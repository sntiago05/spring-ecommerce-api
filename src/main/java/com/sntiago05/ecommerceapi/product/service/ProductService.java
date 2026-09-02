package com.sntiago05.ecommerceapi.product.service;

import com.sntiago05.ecommerceapi.product.dto.ProductCreateRequest;
import com.sntiago05.ecommerceapi.product.entity.Product;
import com.sntiago05.ecommerceapi.product.exceptions.ProductConflictException;
import com.sntiago05.ecommerceapi.product.mapper.ProductMapper;
import com.sntiago05.ecommerceapi.product.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProductService {
    private final ProductRepository repository;

    public Product createProduct(ProductCreateRequest request) {
        if (repository.existsByNameIgnoreCase(request.name()))
            throw new ProductConflictException();
        return repository.save(ProductMapper.toProduct(request));
    }
}
