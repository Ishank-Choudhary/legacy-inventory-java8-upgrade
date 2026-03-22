package com.legacy.inventory.util;

import java.util.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


/**
 * Legacy date utility using java.util.Date.
 * This class provides common operations related to date formatting, parsing, and getting the current date so that the same logic does not
 * have to be written repeatedly in  diff parts of the app.
 */
public final class DateUtil {

    public static final String DEFAULT_PATTERN = "yyyy-MM-dd HH:mm:ss";

    private DateUtil() {
    }

    // we are getting a data from the user for an order and we are formatting it and returning in readable form
    public static String format(LocalDateTime localDateTime) {
        return format(localDateTime, DEFAULT_PATTERN);
    }

    public static String format(LocalDateTime localDateTime, String pattern) {
        if (localDateTime == null) {
            return "";
        }
        if (!ValidationUtil.hasText(pattern)) {
            pattern = DEFAULT_PATTERN;
        }
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(pattern);
        return dtf.format(localDateTime);
    }

    public static LocalDateTime parse(String text) {
        return parse(text, DEFAULT_PATTERN);
    }

    public static LocalDateTime parse(String text, String pattern) {
        if (!ValidationUtil.hasText(text)) {
            return null;
        }
        if (!ValidationUtil.hasText(pattern)) {
            pattern = DEFAULT_PATTERN;
        }

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(pattern);
        return LocalDateTime.parse(text, dtf);
    }

    public static Date now() {
        return new Date();
    }
}
