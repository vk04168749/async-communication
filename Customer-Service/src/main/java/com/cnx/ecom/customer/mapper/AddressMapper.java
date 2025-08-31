package com.cnx.ecom.customer.mapper;

import com.cnx.ecom.customer.dto.AddressDTO;
import com.cnx.ecom.customer.entity.Address;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AddressMapper {
    Address toEntity(AddressDTO dto);
    AddressDTO toDTO(Address entity);
}
