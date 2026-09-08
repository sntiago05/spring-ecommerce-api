package com.sntiago05.ecommerceapi.order.repository;

import com.sntiago05.ecommerceapi.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("""
                SELECT o FROM Order o
                LEFT JOIN FETCH o.items i
                WHERE o.user.email = :email
            """)
    List<Order> findByUserWithItems(@Param("email") String email);
}
