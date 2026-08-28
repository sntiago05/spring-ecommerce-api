package com.sntiago05.ecommerceapi.cart.repository;

import com.sntiago05.ecommerceapi.cart.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
}
