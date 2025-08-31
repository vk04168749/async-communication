package com.cnx.ecom.payment.dto;

import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentResponseDTO {

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
