package com.cnx.ecom.customer.service;

import com.cnx.ecom.customer.dto.AddressDTO;
import com.cnx.ecom.customer.entity.Address;
import com.cnx.ecom.customer.entity.Customer;
import com.cnx.ecom.customer.exception.CustomerException;
import com.cnx.ecom.customer.mapper.AddressMapper;
import com.cnx.ecom.customer.repository.AddressRepository;
import com.cnx.ecom.customer.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final CustomerRepository customerRepository;

    private final AddressRepository addressRepository;

    private final AddressMapper addressMapper = Mappers.getMapper(AddressMapper.class);

    @Transactional
    public AddressDTO addAddressToCustomer(Long customerId, AddressDTO addressDTO) {

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerException("Customer not found with id: " + customerId, 404));

        Address address = addressMapper.toEntity(addressDTO);
        address.setCustomer(customer);
        customer.getAddresses().add(address);
        Address savedAddress = addressRepository.save(address);
        return addressMapper.toDTO(savedAddress);
    }

}
