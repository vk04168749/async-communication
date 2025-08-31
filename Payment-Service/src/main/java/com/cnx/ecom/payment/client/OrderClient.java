package com.cnx.ecom.payment.client;

import com.cnx.ecom.payment.dto.OrderDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderClient {

    private final WebClient.Builder webClientBuilder;

    @Value("${order.service.url}")
    private String orderServiceUrl;

    public OrderDTO getOrder(Long orderId) {
        return webClientBuilder.build()
                .get()
                .uri(orderServiceUrl + "/api/orders/" + orderId)
                .retrieve()
                .bodyToMono(OrderDTO.class)
                .doOnError(e -> log.error("Error fetching order {}: {}", orderId, e.getMessage()))
                .block(); // block for now; can be fully reactive later
    }
}
