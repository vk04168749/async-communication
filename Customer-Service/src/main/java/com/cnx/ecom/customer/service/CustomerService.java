package com.cnx.ecom.customer.service;

import com.cnx.ecom.customer.dto.CustomerDTO;
import com.cnx.ecom.customer.dto.AddressDTO;
import com.cnx.ecom.customer.entity.Customer;
import com.cnx.ecom.customer.entity.Address;
import com.cnx.ecom.customer.exception.CustomerException;
import com.cnx.ecom.customer.mapper.AddressMapper;
import com.cnx.ecom.customer.mapper.CustomerMapper;
import com.cnx.ecom.customer.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper mapper = Mappers.getMapper(CustomerMapper.class);
    private final AddressMapper addressMapper = Mappers.getMapper(AddressMapper.class);

    @Transactional
    public CustomerDTO createCustomer(CustomerDTO customerDTO) {

        // Check if email exists
        customerRepository.findByEmail(customerDTO.getEmail())
                .ifPresent(c -> { throw new CustomerException("Email already exists", 400); });

        Customer customer = mapper.toEntity(customerDTO);

        // Map addresses
        if (customerDTO.getAddresses() != null) {
            List<Address> addresses = customerDTO.getAddresses().stream()
                    .map(addressDTO -> {
                        Address address = addressMapper.toEntity(addressDTO);
                        address.setCustomer(customer);
                        return address;
                    })
                    .collect(Collectors.toList());
            customer.setAddresses(addresses);
        }

        Customer saved = customerRepository.save(customer);
        return mapper.toDTO(saved);
    }

    public CustomerDTO getCustomerById(Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerException("Customer not found with id: " + customerId, 404));

        return mapper.toDTO(customer);
    }
}
