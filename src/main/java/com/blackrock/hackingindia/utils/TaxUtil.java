package com.blackrock.hackingindia.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class TaxUtil {

    // TAX Slab
    public static double calculateTax(double income) {
        if (income <= 700000) return 0.0;
        
        double tax = 0.0;
        
        if (income > 1500000) {
            tax += (income - 1500000) * 0.30;
            income = 1500000;
        }
        if (income > 1200000) {
            tax += (income - 1200000) * 0.20;
            income = 1200000;
        }
        if (income > 1000000) {
            tax += (income - 1000000) * 0.15;
            income = 1000000;
        }
        if (income > 700000) {
            tax += (income - 700000) * 0.10;
        }
        
        return tax;
    }

    public static BigDecimal calculateNpsTaxBenefit(double wage, double investedAmount) {
        // Rule: Max deduction is 10% of wage OR 2,00,000, whichever is smaller. 
        double maxDeduction = Math.min(wage * 0.10, 200000.0);
        double actualDeduction = Math.min(investedAmount, maxDeduction);

        double standardTax = calculateTax(wage);
        double reducedTax = calculateTax(wage - actualDeduction);
        
        double benefit = standardTax - reducedTax;
        
        return BigDecimal.valueOf(benefit).setScale(2, RoundingMode.HALF_UP);
    }
}
