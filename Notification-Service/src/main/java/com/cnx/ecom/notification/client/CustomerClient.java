package com.cnx.ecom.notification.client;

import com.cnx.ecom.notification.dto.CustomerDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class CustomerClient {

    private final WebClient webClient;
    private final String baseUrl;

    public CustomerClient(WebClient.Builder builder,
                          @Value("${customer.service.url}") String baseUrl) {
        this.webClient = builder.build();
        this.baseUrl = baseUrl;
    }


    public Mono<CustomerDto> getCustomer(Long id) {
        return webClient.get()
                .uri(baseUrl + "/{id}", id)
                .retrieve()
                .bodyToMono(CustomerDto.class)
                .doOnNext(customer -> log.info("Received customer data: {}", customer))
                .doOnError(error -> log.error("Error while fetching customer {}: {}", id, error.getMessage()))
                .onErrorResume(WebClientResponseException.class, ex -> {
                    log.error("Customer API returned status {}: {}", ex.getStatusCode(), ex.getResponseBodyAsString());
                    return Mono.empty(); // or provide fallback Mono.just(new CustomerDto())
                })
                .onErrorResume(Exception.class, ex -> {
                    log.error("Unexpected error while fetching customer {}: {}", id, ex.getMessage(), ex);
                    return Mono.empty();
                });
    }

}
