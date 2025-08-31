package com.cnx.ecom.payment.repository;

import com.cnx.ecom.payment.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Optional<Payment> findByCustomerIdAndOrderId(Long customerId, String orderId);

}
