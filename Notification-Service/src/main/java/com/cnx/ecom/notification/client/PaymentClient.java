package com.cnx.ecom.notification.client;

import com.cnx.ecom.notification.dto.PaymentDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class PaymentClient {

    private final WebClient webClient;
    private final String baseUrl;

    public PaymentClient(WebClient.Builder builder,
                         @Value("${payment.service.url}") String baseUrl) {
        this.webClient = builder.build();
        this.baseUrl = baseUrl;
    }

    public PaymentDto getPayment(Long customerId, Long orderId) {
        return webClient.get()
                .uri(baseUrl + "/" + customerId + "/" + orderId)
                .retrieve()
                .bodyToMono(PaymentDto.class)
                .onErrorResume(ex -> {
                    log.error("Error fetching payment for customerId={} and orderId={}: {}",
                            customerId, orderId, ex.getMessage(), ex);
                    return Mono.empty();
                })
                .blockOptional()
                .orElse(null);
    }
}
