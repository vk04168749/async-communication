package com.cnx.ecom.customer.controller;

import com.cnx.ecom.customer.dto.AddressDTO;
import com.cnx.ecom.customer.dto.CustomerDTO;
import com.cnx.ecom.customer.service.AddressService;
import com.cnx.ecom.customer.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    private final AddressService addressService;

    @PostMapping
    public ResponseEntity<CustomerDTO> createCustomer(@Valid @RequestBody CustomerDTO customerDTO) {
        CustomerDTO saved = customerService.createCustomer(customerDTO);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @PostMapping("/{customerId}/address")
    public ResponseEntity<AddressDTO> addAddress(
            @PathVariable Long customerId,
            @Valid @RequestBody AddressDTO addressDTO) {

        AddressDTO savedAddress = addressService.addAddressToCustomer(customerId, addressDTO);
        return new ResponseEntity<>(savedAddress, HttpStatus.CREATED);
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerDTO> getCustomer(@PathVariable Long customerId) {
        CustomerDTO customerDTO = customerService.getCustomerById(customerId);
        return ResponseEntity.ok(customerDTO);
    }

}
