package com.cnx.ecom.order.dto;

import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InventoryProductDTO {

    private Long id;

    private String name;

    private BigDecimal price;

}
