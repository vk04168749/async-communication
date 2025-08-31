package com.cnx.ecom.inventory.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentDto {

    private Long id;

    private String orderId;

    private Long customerId;

    private String status;

    private String message;

    private Double amount;

}