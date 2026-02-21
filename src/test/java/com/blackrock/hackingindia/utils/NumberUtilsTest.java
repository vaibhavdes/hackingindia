package com.blackrock.hackingindia.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import com.blackrock.hackingindia.exceptions.NegativeNumberException;
import com.blackrock.hackingindia.pojo.Result;

public class NumberUtilsTest {
    
    @Test
    void shouldReturnSameValue_WhenAlreadyMultipleOfHundred() {
        Result result = NumberUtils.calculateCeilingAndRemanent(new BigDecimal("100.0"));

        assertEquals(new BigDecimal("100"), result.getCeiling());
        assertEquals(new BigDecimal("0.0"), result.getRemanent());
    }

    @Test
    void shouldRoundUpCorrectly_For101() {
        Result result = NumberUtils.calculateCeilingAndRemanent(new BigDecimal("101.0"));

        assertEquals(new BigDecimal("200"), result.getCeiling());
        assertEquals(new BigDecimal("99.0"), result.getRemanent());
    }

    @Test
    void shouldRoundUpCorrectly_For250() {
        Result result = NumberUtils.calculateCeilingAndRemanent(new BigDecimal("250.0"));

        assertEquals(new BigDecimal("300"), result.getCeiling());
        assertEquals(new BigDecimal("50.0"), result.getRemanent());
    }

    @Test
    void shouldThrowException_WhenNegativeValue() {
        NegativeNumberException exception =
                assertThrows(NegativeNumberException.class, () ->
                        NumberUtils.checkIfNegative(new BigDecimal("-50.0"))
                );

        assertEquals("Negative amount are not allowed", exception.getMessage());
    }
}
