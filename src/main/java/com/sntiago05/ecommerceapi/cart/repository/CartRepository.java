package com.sntiago05.ecommerceapi.cart.repository;

import com.sntiago05.ecommerceapi.cart.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    @Query("SELECT c FROM Cart c LEFT JOIN FETCH c.items WHERE c.id = :id")
    Optional<Cart> findByIdWithItems(@Param("id") Long id);

    @Query("SELECT c FROM Cart c " +
            "LEFT JOIN FETCH c.items i " +
            "LEFT JOIN FETCH i.product " +
            "WHERE c.user.email = :email")
    Optional<Cart> findByEmailWithItems(@Param("email") String email);
}
