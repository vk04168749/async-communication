package com.cnx.ecom.notification.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class PaymentDto {
    private Long id;
    private String orderId;
    private Long customerId;
    private String paymentType;
    private String transactionId;
    private String referenceId;
    private Double amount;
    private LocalDateTime date;
    private String status;
    private String message;
}
