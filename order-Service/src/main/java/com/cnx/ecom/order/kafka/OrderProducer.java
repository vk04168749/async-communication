package com.cnx.ecom.order.kafka;

import com.cnx.ecom.order.dto.OrderDTO;
import com.cnx.ecom.order.util.ObjectMapperUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicLong;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderProducer {

    private static final AtomicLong ID_GENERATOR = new AtomicLong(System.currentTimeMillis());

    private final KafkaTemplate<String, OrderDTO> kafkaTemplate;

    private static final String TOPIC = "orders";

    public void sendOrder(OrderDTO event) {
        long key = ID_GENERATOR.incrementAndGet();
        kafkaTemplate.send(TOPIC, String.valueOf(key), event);
        log.info("Order event sent to topic - event - key{}: {}: {}", TOPIC, ObjectMapperUtil.toJson(event), key);
    }
}
