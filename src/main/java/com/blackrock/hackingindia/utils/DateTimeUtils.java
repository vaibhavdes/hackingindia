package com.blackrock.hackingindia.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtils {

    private static String dateTimeFormat = "yyyy-MM-dd HH:mm:ss";

    // Date Format : date": "2023-02-28 15:49:20"
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(dateTimeFormat);
 
    public static LocalDateTime parseToLocalDateTime(String dateString) {
        return LocalDateTime.parse(dateString, FORMATTER);
    }

    public static String format(LocalDateTime dateTime) {
        return dateTime.format(FORMATTER);
    }
}
