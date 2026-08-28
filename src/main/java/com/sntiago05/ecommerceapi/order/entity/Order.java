package com.sntiago05.ecommerceapi.order.entity;

import com.sntiago05.ecommerceapi.user.entity.User;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal total;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
