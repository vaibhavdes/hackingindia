package com.blackrock.hackingindia.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.blackrock.hackingindia.pojo.Result;

public class NumberUtils {
    
    private static final double HUNDRED = 100.0;
    
    private static BigDecimal hundred = new BigDecimal("100");

    public static Result calculateCeilingAndRemanent(BigDecimal value) {

        if (value.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Expense cannot be negative");
        }
        //double ceiling = Math.ceil(value / HUNDRED) * HUNDRED;
        //double remanent = ceiling - value;

        BigDecimal ceiling = value.divide(hundred, 0, RoundingMode.CEILING).multiply(hundred);
        BigDecimal remanent = ceiling.subtract(value);

        return new Result(ceiling, remanent);
    }

}
