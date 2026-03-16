package com.legacy.inventory.util;

import com.legacy.inventory.util.DateUtil;

import java.time.LocalDateTime;

public class DateUtilTest {

    public static void main(String[] args) {

        String input = "2025-03-17 10:30:00";

        // Test parse
        LocalDateTime parsed = DateUtil.parse(input);
        System.out.println("Parsed LocalDateTime: " + parsed);

        // Test format
        String formatted = DateUtil.format(parsed);
        System.out.println("Formatted Date: " + formatted);

        // Round-trip test (important)
        if (input.equals(formatted)) {
            System.out.println("Parse and Format working correctly");
        } else {
            System.out.println("Mismatch between parse and format");
        }

        // Test null input
        LocalDateTime nullTest = DateUtil.parse(null);
        System.out.println("Null parse result: " + nullTest);
    }
}