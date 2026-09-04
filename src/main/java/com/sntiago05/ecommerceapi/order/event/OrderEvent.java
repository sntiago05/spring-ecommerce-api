package com.sntiago05.ecommerceapi.order.event;

import java.math.BigDecimal;
import java.util.List;

public record OrderEvent(Long id, String name, String email, List<OrderItemEvent> items, BigDecimal total) {
}
