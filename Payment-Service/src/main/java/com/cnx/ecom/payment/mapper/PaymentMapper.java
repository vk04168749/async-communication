package com.cnx.ecom.payment.mapper;

import com.cnx.ecom.payment.dto.PaymentRequestDTO;
import com.cnx.ecom.payment.dto.PaymentResponseDTO;
import com.cnx.ecom.payment.entity.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PaymentMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "transactionId", expression = "java(java.util.UUID.randomUUID().toString())")
    @Mapping(target = "date", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "status", constant = "SUCCESS")
    @Mapping(target = "message", constant = "Payment processed successfully")
    Payment toEntity(PaymentRequestDTO dto);

    PaymentResponseDTO toDTO(Payment payment);
}
