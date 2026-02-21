package com.blackrock.hackingindia.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

public class DateTimeUtilsTest {
    
    private static final String VALID_DATE = "2023-02-28 15:49:20";
    private static final String INVALID_DATE = "2023/02/28";

    @Test
    void shouldParseValidStringToLocalDateTime() {
        LocalDateTime result = DateTimeUtils.parseToLocalDateTime(VALID_DATE);

        assertNotNull(result);
        assertEquals(2023, result.getYear());
        assertEquals(2, result.getMonthValue());
        assertEquals(28, result.getDayOfMonth());
        assertEquals(15, result.getHour());
        assertEquals(49, result.getMinute());
        assertEquals(20, result.getSecond());
    }

    @Test
    void shouldThrowExceptionForInvalidDateFormat() {
        assertThrows(Exception.class, () ->
                DateTimeUtils.parseToLocalDateTime(INVALID_DATE)
        );
    }
}
