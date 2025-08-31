package com.cnx.ecom.inventory.kafka;

import com.cnx.ecom.inventory.client.ExternalClient;
import com.cnx.ecom.inventory.dto.OrderDto;
import com.cnx.ecom.inventory.dto.PaymentDto;
import com.cnx.ecom.inventory.entity.Product;
import com.cnx.ecom.inventory.repository.ProductRepository;
import com.cnx.ecom.inventory.util.JsonLogger;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderConsumer {

    private final ObjectMapper objectMapper;
    private final ExternalClient externalClient;
    private final ProductRepository productRepository;
    private final JsonLogger jsonLogger;

    @KafkaListener(topics = "orders", groupId = "inventory-group")
    public void consume(String message) {
        try {
            log.info("Received Kafka order event");
            log.info("Raw message: {}", message);

            OrderDto order = objectMapper.readValue(message, OrderDto.class);
            jsonLogger.logObject("Deserialized Order", order);

            PaymentDto payment = externalClient.getPayment(order.getCustomerId(), order.getOrderId());
            if (payment != null) jsonLogger.logObject("Payment Response", payment);

            OrderDto orderDetails = externalClient.getOrder(order.getOrderId());
            if (orderDetails != null) jsonLogger.logObject("Order Details Response", orderDetails);

            if (payment != null && "SUCCESS".equalsIgnoreCase(payment.getStatus()) &&
                orderDetails != null && "PLACED".equalsIgnoreCase(orderDetails.getStatus())) {

                order.getItems().forEach(item -> {
                    Product product = productRepository.findById(item.getProductId())
                            .orElseThrow(() -> new RuntimeException("Product not found: " + item.getProductId()));

                    log.info("Current stock for product {}: {}", product.getName(), product.getStockQuantity());

                    if (product.getStockQuantity() < item.getQuantity()) {
                        log.warn("Insufficient stock for product {}", product.getName());
                        return;
                    }

                    product.setStockQuantity(product.getStockQuantity() - item.getQuantity());
                    productRepository.save(product);

                    log.info("Updated stock for product {}: new quantity {}", product.getName(), product.getStockQuantity());
                });
            } else {
                log.warn("Skipping stock update: Payment/Order validation failed for order {}", order.getOrderId());
            }

        } catch (Exception ex) {
            log.error("Error processing order event: {}", ex.getMessage(), ex);
        }
    }
}
