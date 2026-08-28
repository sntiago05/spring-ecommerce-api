package com.sntiago05.ecommerceapi.order.repository;

import com.sntiago05.ecommerceapi.order.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
