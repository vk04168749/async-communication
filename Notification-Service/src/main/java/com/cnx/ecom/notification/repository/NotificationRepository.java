package com.cnx.ecom.notification.repository;

import com.cnx.ecom.notification.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
