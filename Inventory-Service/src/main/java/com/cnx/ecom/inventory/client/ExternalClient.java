package com.cnx.ecom.inventory.client;

import com.cnx.ecom.inventory.dto.OrderDto;
import com.cnx.ecom.inventory.dto.PaymentDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@Slf4j
public class ExternalClient {

    private final WebClient.Builder webClientBuilder;

    @Value("${payment.service.url}")
    private String paymentServiceUrl;

    @Value("${order.service.url}")
    private String orderServiceUrl;

    public PaymentDto getPayment(Long customerId, Long orderId) {
        return webClientBuilder.build().get()
                .uri(paymentServiceUrl + "/" + customerId + "/" + orderId)
                .retrieve()
                .bodyToMono(PaymentDto.class)
                .onErrorResume(ex -> {
                    log.error("Error fetching payment: {}", ex.getMessage());
                    return Mono.empty();
                })
                .block();
    }

    public OrderDto getOrder(Long orderId) {
        return webClientBuilder.build().get()
                .uri(orderServiceUrl + "/" + orderId)
                .retrieve()
                .bodyToMono(OrderDto.class)
                .onErrorResume(ex -> {
                    log.error("Error fetching order: {}", ex.getMessage());
                    return Mono.empty();
                })
                .block();
    }
}
