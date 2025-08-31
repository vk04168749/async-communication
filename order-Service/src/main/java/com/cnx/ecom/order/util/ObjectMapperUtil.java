package com.cnx.ecom.order.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

@UtilityClass
@Slf4j
public class ObjectMapperUtil {

    private static final ObjectMapper mapper = new ObjectMapper();

    /**
     * Convert object to JSON string
     *
     * @param obj object to serialize
     * @return JSON string or null if failed
     */
    public static String toJson(Object obj) {
        try {
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            log.error("Failed to serialize object to JSON: {}", obj, e);
            return null;
        }
    }

    /**
     * Convert JSON string to object of given class
     *
     * @param json JSON string
     * @param clazz target class
     * @param <T> type
     * @return object or null if failed
     */
    public static <T> T fromJson(String json, Class<T> clazz) {
        try {
            return mapper.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            log.error("Failed to deserialize JSON: {}", json, e);
            return null;
        }
    }
}
