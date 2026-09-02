package com.sntiago05.ecommerceapi.product.controller;

import com.sntiago05.ecommerceapi.product.dto.ProductCreateRequest;
import com.sntiago05.ecommerceapi.product.dto.ProductResponse;
import com.sntiago05.ecommerceapi.product.entity.Product;
import com.sntiago05.ecommerceapi.product.mapper.ProductMapper;
import com.sntiago05.ecommerceapi.product.service.ProductService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@AllArgsConstructor
@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService service;

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody ProductCreateRequest request) {
        Product created = service.createProduct(request);
        return ResponseEntity.created(URI.create("/products/" + created.getId())).body(ProductMapper.toResponse(created));
    }
}
