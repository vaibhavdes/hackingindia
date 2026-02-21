package com.blackrock.hackingindia.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import com.blackrock.hackingindia.pojo.Result;

public class NumberUtilsTest {
    
    @Test
    void shouldCalculateCeilingAndRemanent_For120() {
        Result result = NumberUtils.calculateCeilingAndRemanent(new BigDecimal(120.0));

        assertEquals(200.0, result.getCeiling());
        assertEquals(80.0, result.getRemanent());
    }

    @Test
    void shouldReturnSameValue_WhenAlreadyMultipleOfHundred() {
        Result result = NumberUtils.calculateCeilingAndRemanent(new BigDecimal(100.0));

        assertEquals(100.0, result.getCeiling());
        assertEquals(0.0, result.getRemanent());
    }

    @Test
    void shouldRoundUpCorrectly_For101() {
        Result result = NumberUtils.calculateCeilingAndRemanent(new BigDecimal(101.0));

        assertEquals(200.0, result.getCeiling());
        assertEquals(99.0, result.getRemanent());
    }

    @Test
    void shouldRoundUpCorrectly_For250() {
        Result result = NumberUtils.calculateCeilingAndRemanent(new BigDecimal(250.0));

        assertEquals(300.0, result.getCeiling());
        assertEquals(50.0, result.getRemanent());
    }

    @Test
    void shouldThrowException_WhenNegativeValue() {
        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class, () ->
                        NumberUtils.calculateCeilingAndRemanent(new BigDecimal(-50.0))
                );

        assertEquals("Receipt amount cannot be negative", exception.getMessage());
    }
}
