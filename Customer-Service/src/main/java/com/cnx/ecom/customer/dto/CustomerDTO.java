package com.cnx.ecom.customer.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerDTO {

    private Long id;

    @NotBlank(message = "{customer.firstname.notblank}")
    private String firstName;

    @NotBlank(message = "{customer.lastname.notblank}")
    private String lastName;

    @Email(message = "{customer.email.invalid}")
    @NotBlank(message = "{customer.email.notblank}")
    private String email;

    @NotBlank(message = "{customer.country.notblank}")
    private String country;

    private List<AddressDTO> addresses;

}
