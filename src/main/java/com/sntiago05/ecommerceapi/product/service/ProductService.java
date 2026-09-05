package com.sntiago05.ecommerceapi.product.service;

import com.sntiago05.ecommerceapi.product.dto.ProductCreateRequest;
import com.sntiago05.ecommerceapi.product.dto.ProductResponse;
import com.sntiago05.ecommerceapi.product.dto.ProductUpdateRequest;
import com.sntiago05.ecommerceapi.product.entity.Product;
import com.sntiago05.ecommerceapi.product.exceptions.ProductConflictException;
import com.sntiago05.ecommerceapi.product.exceptions.ProductNotFoundException;
import com.sntiago05.ecommerceapi.product.exceptions.ProductOutOfStockException;
import com.sntiago05.ecommerceapi.product.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@AllArgsConstructor
public class ProductService {
    private final ProductRepository repository;

    /**
     * Creates a new product based on the provided product creation request.
     *
     * @param request the product creation request containing the details of the product to be created
     * @return the created {@code ProductResponse} DTO after it has been saved to the repository
     * @throws ProductConflictException if a product with the same name already exists (case-insensitive)
     */
    public ProductResponse createProduct(ProductCreateRequest request) {
        if (repository.existsByNameIgnoreCase(request.name()))
            throw new ProductConflictException();
        Product savedProduct = repository.save(ProductCreateRequest.toEntity(request));
        return ProductResponse.fromEntity(savedProduct);
    }

    /**
     * Decreases the stock of a specific product by the specified amount.
     *
     * @param id    the unique identifier of the product whose stock is to be decreased
     * @param stock the quantity to decrease from the product's stock
     * @throws ProductNotFoundException   if no product exists with the given id
     * @throws ProductOutOfStockException if the current stock is less than the specified quantity to decrease
     */
    @Transactional
    public void decreaseStock(Long id, Integer stock) {
        Product product = repository.findByIdForUpdate(id).orElseThrow(() -> new ProductNotFoundException(id));
        if (product.getStock() < stock) throw new ProductOutOfStockException(product.getName());
        product.setStock(product.getStock() - stock);
    }

    public Product findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
    }

    /**
     * Retrieves a paginated list of all products wrapped in a {@code Page} object.
     * Each product is converted to a {@code ProductResponse} object.
     *
     * @param pageable the pagination information, including page number, size, and sorting criteria
     * @return a {@code Page} of {@code ProductResponse} objects containing product details
     */
    @Transactional(readOnly = true)
    public Page<ProductResponse> findAll(Pageable pageable) {

        return repository.findByActiveTrue(pageable).map(ProductResponse::fromEntity);
    }

    @Transactional
    public ProductResponse updateProduct(Long id, ProductUpdateRequest request) {
        Product product = repository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
        if (request.name() != null && !request.name().isBlank()) {
            if (!product.getName().equalsIgnoreCase(request.name())
                    && repository.existsByNameIgnoreCase(request.name())) {
                throw new ProductConflictException();
            }
            product.setName(request.name());
        }
        if (request.description() != null && !request.description().isBlank())
            product.setDescription(request.description());
        if (request.price() != null) product.setPrice(request.price());
        return ProductResponse.fromEntity(product);
    }

    @Transactional
    public void deleteProduct(Long id) {
        Product product = repository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
        product.setActive(false);
    }
}
