package com.cnx.ecom.customer.mapper;

import com.cnx.ecom.customer.dto.CustomerDTO;
import com.cnx.ecom.customer.entity.Customer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    Customer toEntity(CustomerDTO dto);
    CustomerDTO toDTO(Customer entity);
}
