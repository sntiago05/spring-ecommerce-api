package com.sntiago05.ecommerceapi.infrastructure.messaging;

import com.sntiago05.ecommerceapi.config.RabbitProperties;
import com.sntiago05.ecommerceapi.order.event.OrderEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
@RequiredArgsConstructor
public class CreatedOrderListener {
    private final RabbitTemplate rabbitTemplate;
    private final RabbitProperties rabbitProperties;
     @Value("${app.rabbitmq.routing-key-order-created}")
    private String routingKey;
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onCreatedOrder(OrderEvent event) {
        try {
            rabbitTemplate.convertAndSend(rabbitProperties.exchange(),routingKey,event);
            log.info("Order event sent to RabbitMQ");
        } catch (AmqpException e) {
            log.error("Error sending order event to RabbitMQ", e);
        }
    }
}
