package com.cnx.ecom.order.repository;

import com.cnx.ecom.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
