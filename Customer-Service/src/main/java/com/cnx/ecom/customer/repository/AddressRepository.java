package com.cnx.ecom.customer.repository;

import com.cnx.ecom.customer.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
