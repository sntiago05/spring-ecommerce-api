package com.sntiago05.ecommerceapi.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.rabbit")
public record RabbitProperties(
        String exchange,
        String queue,
        String routingKey
) {
}
