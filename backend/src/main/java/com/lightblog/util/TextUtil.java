package com.lightblog.util;

public final class TextUtil {
    private TextUtil() {
    }

    public static boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    public static String defaultIfBlank(String value, String fallback) {
        return isBlank(value) ? fallback : value.trim();
    }
}
