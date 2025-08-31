package com.cnx.ecom.payment.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class OrderDTO {
	
    private Long orderId;
    
    private Long customerId;
    
    private String customerName;
    
    private BigDecimal totalAmount;
    
    private String status;

}
