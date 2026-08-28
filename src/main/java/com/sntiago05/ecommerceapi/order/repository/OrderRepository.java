package com.sntiago05.ecommerceapi.order.repository;

import com.sntiago05.ecommerceapi.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
