package com.sntiago05.ecommerceapi.order.entity;

import com.sntiago05.ecommerceapi.product.entity.Product;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Builder
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    @Column(precision = 10, scale = 2)
    private BigDecimal subtotal;
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
    @Column(name = "unit_price", precision = 10, scale = 2)
    private BigDecimal unitPrice;
    private Integer quantity;
}
