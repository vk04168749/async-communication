package com.cnx.ecom.notification.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "notifications")
@Data
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long customerId;

    private String email;

    private String subject;

    @Column(length = 5000)
    private String body;
}
