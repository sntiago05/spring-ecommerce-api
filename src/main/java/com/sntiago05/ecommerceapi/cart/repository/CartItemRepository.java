package com.sntiago05.ecommerceapi.cart.repository;

import com.sntiago05.ecommerceapi.cart.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}
