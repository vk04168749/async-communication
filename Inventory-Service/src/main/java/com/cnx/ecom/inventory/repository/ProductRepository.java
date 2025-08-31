package com.cnx.ecom.inventory.repository;

import com.cnx.ecom.inventory.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
