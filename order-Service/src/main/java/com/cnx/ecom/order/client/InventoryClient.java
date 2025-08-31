package com.cnx.ecom.order.client;

import com.cnx.ecom.order.dto.InventoryProductDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class InventoryClient {

    private final WebClient.Builder webClientBuilder;

    @Value("${inventory.service.url}")
    private String inventoryServiceUrl;

    public List<InventoryProductDTO> getProductsByIds(List<Long> ids) {
        String idParam = String.join(",", ids.stream().map(String::valueOf).toList());

        return webClientBuilder.build()
                .get()
                .uri(inventoryServiceUrl + "/api/products/ids?ids=" + idParam)
                .retrieve()
                .bodyToFlux(InventoryProductDTO.class)
                .collectList()
                .doOnError(e -> log.error("Error fetching products from inventory: {}", e.getMessage()))
                .block();
    }
}
