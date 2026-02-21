package com.blackrock.hackingindia.config;

public enum InvestmentType {
    
    NPS(0.0711, true),
    INDEX(0.1449, false),
    FIXED_DEPOSIT(0.065, true);

    private final double annualRate;
    private final boolean givesTaxBenefit;

    InvestmentType(double annualRate, boolean givesTaxBenefit) {
        this.annualRate = annualRate;
        this.givesTaxBenefit = givesTaxBenefit;
    }

    public double getAnnualRate() {
        return annualRate;
    }

    public boolean givesTaxBenefit() {
        return givesTaxBenefit;
    }
}