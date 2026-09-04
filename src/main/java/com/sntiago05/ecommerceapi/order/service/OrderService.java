package com.sntiago05.ecommerceapi.order.service;

import com.sntiago05.ecommerceapi.order.entity.Order;
import com.sntiago05.ecommerceapi.order.repository.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    public Order saveOrder(Order order) {
        return orderRepository.save(order);
    }
}
