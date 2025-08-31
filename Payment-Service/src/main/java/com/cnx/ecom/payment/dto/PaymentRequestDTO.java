package com.cnx.ecom.payment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentRequestDTO {

    private Long id;

    @NotBlank(message = "Order ID is required")
    private String orderId;

    @NotNull(message = "Customer ID is required")
    private Long customerId;

    @NotBlank(message = "Payment type is required")
    private String paymentType;

    @NotBlank(message = "Reference ID is required")
    private String referenceId;

}
