package com.sntiago05.ecommerceapi.cart.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Builder
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany(mappedBy = "cart", orphanRemoval = true)
    private List<CartItem> items = new ArrayList<>();

    public BigDecimal calculateTotal() {
        return this.items.stream().map(CartItem::calculateSubTotal).reduce(BigDecimal.ZERO,
                BigDecimal::add);
    }
}
