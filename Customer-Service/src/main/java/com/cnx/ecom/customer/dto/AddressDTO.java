package com.cnx.ecom.customer.dto;

import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressDTO {

    private Long id;

    @NotBlank(message = "{address.street.notblank}")
    private String street;

    @NotBlank(message = "{address.city.notblank}")
    private String city;

    @NotBlank(message = "{address.state.notblank}")
    private String state;

    @NotBlank(message = "{address.zipcode.notblank}")
    private String zipCode;

}
