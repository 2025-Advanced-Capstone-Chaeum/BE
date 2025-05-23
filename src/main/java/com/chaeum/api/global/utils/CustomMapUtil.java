package com.chaeum.api.global.utils;

import lombok.experimental.UtilityClass;

import java.util.Map;

@UtilityClass
public class CustomMapUtil {

    @SuppressWarnings("unchecked")
    public static Map<String, Object> getNestedMap(Map<String, Object> source, String key) {
        Object value = source.get(key);
        return value instanceof Map ? (Map<String, Object>) value : Map.of();
    }
}
