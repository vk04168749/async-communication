package com.cnx.ecom.payment.controller;

import com.cnx.ecom.payment.dto.PaymentRequestDTO;
import com.cnx.ecom.payment.dto.PaymentResponseDTO;
import com.cnx.ecom.payment.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService service;

    @PostMapping
    public ResponseEntity<PaymentResponseDTO> makePayment(@Valid @RequestBody PaymentRequestDTO request){
        PaymentResponseDTO response = service.processPayment(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{customerId}/{orderId}")
    public ResponseEntity<PaymentResponseDTO> getPayment(@PathVariable Long customerId,
                                                         @PathVariable String orderId){
        PaymentResponseDTO response = service.getPayment(customerId, orderId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
