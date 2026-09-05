package com.sntiago05.ecommerceapi.cart.entity;

import com.sntiago05.ecommerceapi.product.entity.Product;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "cart_items")
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer quantity;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

    public BigDecimal calculateSubTotal() {
        return this.product.getPrice().multiply(BigDecimal.valueOf(quantity));
    }
}
