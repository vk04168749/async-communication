package com.cnx.ecom.inventory.dto;

import lombok.*;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class OrderDto {

    private Long orderId;

    private Long customerId;

    private String status;

    private List<OrderItemDto> items;

}



