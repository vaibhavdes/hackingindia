package com.blackrock.hackingindia.scenario;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import com.blackrock.hackingindia.utils.InvestmentUtil;
import com.blackrock.hackingindia.utils.TaxUtil;

public class InvestTest {

    @Test
    void testNps() {
        
        double savedAmount = 145.0; 
        int age = 29;              
        double inflation = 5.5;     
        double wage = 50000;

        double npsRate = 0.0711; // 7.11%

        BigDecimal expectedProfit = new BigDecimal("86.88");
        BigDecimal actualProfit = InvestmentUtil.calculateProfit(savedAmount, npsRate, age, inflation);
        
        assertEquals(expectedProfit, actualProfit, "calculated wrong profit!");

        BigDecimal expectedTaxBenefit = new BigDecimal("0.00");
        
        BigDecimal actualTaxBenefit = TaxUtil.calculateNpsTaxBenefit(wage, savedAmount);
        
        assertEquals(expectedTaxBenefit, actualTaxBenefit, "calculated the wrong tax benefit!");
    }

    @Test
    void testNiftyIndex() {
        
        double savedAmount = 145.0; 
        int age = 29;               
        double inflation = 5.5;     

        double indexRate = 0.1449; // 14.49%

        BigDecimal expectedProfit = new BigDecimal("1684.51"); 
        BigDecimal actualProfit = InvestmentUtil.calculateProfit(savedAmount, indexRate, age, inflation);
        
        assertEquals(expectedProfit, actualProfit, "calculated the wrong profit!");
    }
}
