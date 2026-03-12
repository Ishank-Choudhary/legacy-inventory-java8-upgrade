package com.legacy.inventory.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Legacy date utility using java.util.Date.
 */
public final class DateUtil {

    public static final String DEFAULT_PATTERN = "yyyy-MM-dd HH:mm:ss";

    private DateUtil() {
    }

    public static String format(Date date) {
        return format(date, DEFAULT_PATTERN);
    }

    public static String format(Date date, String pattern) {
        if (date == null) {
            return "";
        }
        if (!ValidationUtil.hasText(pattern)) {
            pattern = DEFAULT_PATTERN;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }

    public static Date parse(String text) {
        return parse(text, DEFAULT_PATTERN);
    }

    public static Date parse(String text, String pattern) {
        if (!ValidationUtil.hasText(text)) {
            return null;
        }
        if (!ValidationUtil.hasText(pattern)) {
            pattern = DEFAULT_PATTERN;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        try {
            return sdf.parse(text);
        } catch (ParseException e) {
            System.out.println("Could not parse date: " + text + " using pattern " + pattern);
            return null;
        }
    }

    public static Date now() {
        return new Date();
    }
}
