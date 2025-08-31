package com.cnx.ecom.notification.dto;

import lombok.Data;
import java.util.List;

@Data
public class OrderDto {
    private Long orderId;
    private Long customerId;
    private String customerName;
    private Double totalAmount;
    private String status;
    private List<OrderItemDto> items;
    private AddressDto shippingAddress;
    private AddressDto billingAddress;
}
