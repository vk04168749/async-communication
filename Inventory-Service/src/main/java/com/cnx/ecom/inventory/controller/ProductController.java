package com.cnx.ecom.inventory.controller;

import com.cnx.ecom.inventory.entity.Product;
import com.cnx.ecom.inventory.repository.ProductRepository;
import com.cnx.ecom.inventory.util.JsonLogger;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private final ProductRepository productRepository;

    private final JsonLogger jsonLogger;

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productRepository.findAll();
        jsonLogger.logObject("All Products Fetched", products);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/ids")
    public ResponseEntity<List<Product>> getProductsByIds(@RequestParam String ids) {
        List<Long> productIds = Arrays.stream(ids.split(","))
                .map(Long::parseLong)
                .toList();
        List<Product> products = productRepository.findAllById(productIds);
        jsonLogger.logObject("Products Fetched By IDs", products);
        return ResponseEntity.ok(products);
    }
}
