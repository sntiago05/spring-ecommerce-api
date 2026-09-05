package com.sntiago05.ecommerceapi.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.rabbitmq")
public record RabbitProperties(
        String exchange,
        String queue,
        String routingKey
) {
}
