package com.legacy.inventory.util;

/**
 * Classic validation helpers.
 */
public final class ValidationUtil {

    private ValidationUtil() {
    }

    public static boolean hasText(String text) {
        return text != null && text.trim().length() > 0;
    }

    public static boolean isPositive(int value) {
        return value > 0;
    }

    public static boolean isNonNegative(int value) {
        return value >= 0;
    }

    public static boolean isNonNegative(double value) {
        return value >= 0;
    }
}
