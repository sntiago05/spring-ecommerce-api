package com.sntiago05.ecommerceapi.order.service;

import com.sntiago05.ecommerceapi.config.CurrentUserService;
import com.sntiago05.ecommerceapi.order.dto.OrderResponse;
import com.sntiago05.ecommerceapi.order.entity.Order;
import com.sntiago05.ecommerceapi.order.repository.OrderRepository;
import com.sntiago05.ecommerceapi.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final CurrentUserService currentUserService;

    public Order saveOrder(Order order) {
        return orderRepository.save(order);
    }

    @Transactional(readOnly = true)
    public List<OrderResponse> findOrdersByCurrentUser() {
        return orderRepository.findByUserWithItems(currentUserService.getCurrentUserEmail()).stream()
                .map(OrderResponse::fromEntity).toList();
    }
}
