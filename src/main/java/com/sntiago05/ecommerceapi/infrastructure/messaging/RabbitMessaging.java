package com.sntiago05.ecommerceapi.infrastructure.messaging;

import com.sntiago05.ecommerceapi.order.event.OrderEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RabbitMessaging {
    @RabbitListener(queues = "${app.rabbitmq.queue}")
    public void onOrderEvent(OrderEvent event) {
        log.info("Order event received from RabbitMQ: {}", event);
    }
}
