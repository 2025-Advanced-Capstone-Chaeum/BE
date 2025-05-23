package com.chaeum.api.global.auth.util;

import lombok.experimental.UtilityClass;

import java.util.Map;

@UtilityClass
public class OAuth2ResponseUtil {

    public static String toStringOrNull(Object object) {
        return object != null ? object.toString() : null;
    }

    public static String toStringOrDefault(Object object, String defaultValue) {
        return object != null ? object.toString() : defaultValue;
    }

    @SuppressWarnings("unchecked")
    public static Map<String, Object> getNestedMap(Map<String, Object> source, String key) {
        Object value = source.get(key);
        return value instanceof Map ? (Map<String, Object>) value : Map.of();
    }
}
