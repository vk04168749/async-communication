package com.cnx.ecom.notification.service;

import com.cnx.ecom.notification.client.CustomerClient;
import com.cnx.ecom.notification.client.PaymentClient;
import com.cnx.ecom.notification.dto.*;
import com.cnx.ecom.notification.entity.Notification;
import com.cnx.ecom.notification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {

    private final CustomerClient customerClient;
    private final PaymentClient paymentClient;
    private final NotificationRepository notificationRepository;

    public void processOrder(OrderDto order) {
        log.info("Processing notification for order {}", order.getOrderId());

        CustomerDto customer = customerClient.getCustomer(order.getCustomerId())
                .blockOptional()
                .orElse(null);
        if(customer == null) {
            log.warn("Customer with id {} not found. Skipping notification.", order.getCustomerId());
            return;
        }

        log.info("Fetched customer: {}", customer);

        PaymentDto payment = paymentClient.getPayment(order.getCustomerId(), order.getOrderId());
        if(payment == null) {
            log.warn("Payment info not found for customerId {} and orderId {}. Skipping notification.",
                    order.getCustomerId(), order.getOrderId());
            return;
        }
        log.info("Fetched payment: {}", payment);

        String letter = prepareLetter(order, customer, payment);
        log.info("Notification Letter:\n{}", letter);

        Notification notification = new Notification();
        notification.setCustomerId(customer.getId());
        notification.setEmail(customer.getEmail());
        notification.setSubject("Order Notification for Order " + order.getOrderId());
        notification.setBody(letter);

        notificationRepository.save(notification);

        log.info("Notification saved with id {}", notification.getId());
    }

    private String prepareLetter(OrderDto order, CustomerDto customer, PaymentDto payment) {
        StringBuilder letter = new StringBuilder();

        letter.append("Dear ")
                .append(customer.getFirstName()).append(" ")
                .append(customer.getLastName()).append(",\n\n")
                .append("Thank you for your order #").append(order.getOrderId()).append(".\n\n")

                .append("Payment Details:\n")
                .append("  Status   : ").append(payment.getStatus()).append(" (").append(payment.getMessage()).append(")\n")
                .append("  Amount   : ").append(payment.getAmount()).append("\n")
                .append("  Type     : ").append(payment.getPaymentType()).append("\n")
                .append("  Reference: ").append(payment.getReferenceId()).append("\n\n");

        letter.append("Shipping Address:\n")
                .append("  ").append(order.getShippingAddress().getStreet()).append("\n")
                .append("  ").append(order.getShippingAddress().getCity()).append(", ")
                .append(order.getShippingAddress().getState()).append("\n")
                .append("  ").append(order.getShippingAddress().getCountry())
                .append(" - ").append(order.getShippingAddress().getZipCode()).append("\n\n");

        letter.append("Billing Address:\n")
                .append("  ").append(order.getBillingAddress().getStreet()).append("\n")
                .append("  ").append(order.getBillingAddress().getCity()).append(", ")
                .append(order.getBillingAddress().getState()).append("\n")
                .append("  ").append(order.getBillingAddress().getCountry())
                .append(" - ").append(order.getBillingAddress().getZipCode()).append("\n\n");

        letter.append("Order Items:\n");
        order.getItems().forEach(item -> {
            letter.append("  Product ID: ").append(item.getProductId())
                    .append(", Quantity: ").append(item.getQuantity())
                    .append(", Price: ").append(item.getPrice())
                    .append("\n");
        });

        letter.append("\nBest regards,\nEcom Team");

        return letter.toString();
    }

}
