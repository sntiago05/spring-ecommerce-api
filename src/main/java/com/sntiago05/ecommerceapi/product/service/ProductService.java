package com.sntiago05.ecommerceapi.product.service;

import com.sntiago05.ecommerceapi.product.dto.ProductCreateRequest;
import com.sntiago05.ecommerceapi.product.entity.Product;
import com.sntiago05.ecommerceapi.product.exceptions.ProductConflictException;
import com.sntiago05.ecommerceapi.product.exceptions.ProductNotFoundException;
import com.sntiago05.ecommerceapi.product.exceptions.ProductOutOfStockException;
import com.sntiago05.ecommerceapi.product.mapper.ProductMapper;
import com.sntiago05.ecommerceapi.product.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class ProductService {
    private final ProductRepository repository;

    public Product createProduct(ProductCreateRequest request) {
        if (repository.existsByNameIgnoreCase(request.name()))
            throw new ProductConflictException();
        return repository.save(ProductMapper.toProduct(request));
    }

    @Transactional
    public Product decreaseStock(Long id, Integer stock) {
        Product product = repository.findByIdForUpdate(id).orElseThrow(() -> new ProductNotFoundException(id));
        if (product.getStock() < stock) throw new ProductOutOfStockException(product.getName());
        product.setStock(product.getStock() - stock);
        return product;
    }

    public Product findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
    }
}
