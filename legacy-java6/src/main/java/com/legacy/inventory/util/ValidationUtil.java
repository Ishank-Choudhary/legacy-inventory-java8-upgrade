package com.legacy.inventory.util;

/**
 * Classic validation helpers.
 * if a class - has only static methods, private constructor, has no instance variables - then it is a UTILITY class
 */
public final class ValidationUtil {

    private ValidationUtil() {  //prevent object creation
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
