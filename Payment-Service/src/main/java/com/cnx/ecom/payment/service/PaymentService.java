package com.cnx.ecom.payment.service;

import com.cnx.ecom.payment.client.OrderClient;
import com.cnx.ecom.payment.dto.PaymentRequestDTO;
import com.cnx.ecom.payment.dto.PaymentResponseDTO;
import com.cnx.ecom.payment.entity.Payment;
import com.cnx.ecom.payment.exception.PaymentException;
import com.cnx.ecom.payment.mapper.PaymentMapper;
import com.cnx.ecom.payment.repository.PaymentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentService {

    private final PaymentRepository paymentRepository;

    private final PaymentMapper mapper;

    private final OrderClient orderClient;

    @Transactional
    public PaymentResponseDTO processPayment(PaymentRequestDTO request) {

        Long orderIdLong;
        try {
            orderIdLong = Long.parseLong(request.getOrderId());
        } catch (NumberFormatException e) {
            throw new PaymentException("Invalid order ID", 400);
        }

        var order = orderClient.getOrder(orderIdLong);
        if (order == null) {
            throw new PaymentException("Order not found with ID: " + request.getOrderId(), 404);
        }

        double amount = order.getTotalAmount().doubleValue();
        if (amount <= 0) {
            throw new PaymentException("Order amount must be greater than zero", 400);
        }

        // Map request to entity
        Payment payment = mapper.toEntity(request);
        payment.setAmount(amount);

        Payment saved = paymentRepository.save(payment);
        log.info("Payment saved: {}", saved);

        return mapper.toDTO(saved);
    }

    public PaymentResponseDTO getPayment(Long customerId, String orderId){
        Payment payment = paymentRepository.findByCustomerIdAndOrderId(customerId, orderId)
                .orElseThrow(() -> new PaymentException("Payment not found",404));
        return mapper.toDTO(payment);
    }
}
