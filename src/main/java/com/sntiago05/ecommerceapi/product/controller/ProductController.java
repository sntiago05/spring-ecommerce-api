package com.sntiago05.ecommerceapi.product.controller;

import com.sntiago05.ecommerceapi.product.dto.PageResponse;
import com.sntiago05.ecommerceapi.product.dto.ProductCreateRequest;
import com.sntiago05.ecommerceapi.product.dto.ProductResponse;
import com.sntiago05.ecommerceapi.product.dto.ProductUpdateRequest;
import com.sntiago05.ecommerceapi.product.service.ProductService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@AllArgsConstructor
@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService service;

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody ProductCreateRequest request) {
        ProductResponse response = service.createProduct(request);
        return ResponseEntity.created(URI.create("/products/" + response.id())).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> findById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.findByIdResponse(id));
    }

    @GetMapping
    public ResponseEntity<PageResponse<ProductResponse>> findAll(Pageable pageable) {
        return ResponseEntity.ok(
                PageResponse.from(service.findAll(pageable))
        );
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable Long id, @Valid @RequestBody ProductUpdateRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(service.updateProduct(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        service.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

}
