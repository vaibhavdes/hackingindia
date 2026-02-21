package com.blackrock.hackingindia.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class InvestmentUtil {
    
    public static BigDecimal calculateProfit(double principal, double annualRate, int age, double inflationRatePercent) {
        if (principal <= 0) return BigDecimal.ZERO;

        int years = (age < 60) ? (60 - age) : 5;
        
        // Compound Interest -> A = P * (1 + r)^t
        double futureValue = principal * Math.pow(1.0 + annualRate, years);
        
        // Inflation -> A / (1 + inflation)^t
        double inflationDecimal = inflationRatePercent / 100.0;
        double realValue = futureValue / Math.pow(1.0 + inflationDecimal, years);
        
        double profit = realValue - principal;
        
        return BigDecimal.valueOf(profit).setScale(2, RoundingMode.HALF_UP);
    }
}
