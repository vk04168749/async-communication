package com.cnx.ecom.notification.dto;

import lombok.Data;
import java.util.List;

@Data
public class CustomerDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String country;
    private List<AddressDto> addresses;
}
