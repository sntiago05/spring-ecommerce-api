package com.sntiago05.ecommerceapi.product.mapper;

import com.sntiago05.ecommerceapi.product.dto.ProductCreateRequest;
import com.sntiago05.ecommerceapi.product.dto.ProductResponse;
import com.sntiago05.ecommerceapi.product.entity.Product;

public class ProductMapper {

    private ProductMapper() {
    }

    public static Product toProduct(ProductCreateRequest request) {
        return Product.builder().name(request.name())
                .description(request.description())
                .price(request.price())
                .stock(request.stock())
                .build();
    }

    public static ProductResponse toResponse(Product product) {
        return new ProductResponse(product.getId(), product.getName(), product.getDescription(), product.getPrice(), product.getStock());
    }
}
