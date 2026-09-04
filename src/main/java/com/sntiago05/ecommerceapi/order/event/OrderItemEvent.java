package com.sntiago05.ecommerceapi.order.event;

import java.math.BigDecimal;

public record OrderItemEvent(Long productId,String name, Integer quantity, BigDecimal price) {
}
