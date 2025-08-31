package com.cnx.ecom.inventory.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class JsonLogger {

    private final ObjectMapper objectMapper;

    public void logObject(String message, Object obj) {
        try {
            String json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
            log.info("{}:\n{}", message, json);
        } catch (JsonProcessingException e) {
            log.error("Error logging object: {}", e.getMessage(), e);
        }
    }
}
