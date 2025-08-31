package com.cnx.ecom.notification.dto;

import lombok.Data;

@Data
public class OrderItemDto {
    private Long productId;
    private String productName;
    private Double price;
    private Integer quantity;
}
