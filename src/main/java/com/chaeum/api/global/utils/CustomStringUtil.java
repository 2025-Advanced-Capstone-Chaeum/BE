package com.chaeum.api.global.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class CustomStringUtil {

    public static String toStringOrNull(Object object) {
        return object != null ? object.toString() : null;
    }

    public static String toStringOrDefault(Object object, String defaultValue) {
        return object != null ? object.toString() : defaultValue;
    }
}
