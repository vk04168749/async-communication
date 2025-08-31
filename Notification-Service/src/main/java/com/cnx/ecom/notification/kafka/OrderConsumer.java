package com.cnx.ecom.notification.kafka;

import com.cnx.ecom.notification.dto.OrderDto;
import com.cnx.ecom.notification.service.NotificationService;
import com.cnx.ecom.notification.util.JsonUtil;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderConsumer {

    private final NotificationService notificationService;

    @KafkaListener(topics = "orders", groupId = "notification-group")
    public void consume(ConsumerRecord<String, String> record) {
        log.info("Received order event: {}", record.value());
        OrderDto order = JsonUtil.fromJson(record.value(), OrderDto.class);
        notificationService.processOrder(order);
    }
}
