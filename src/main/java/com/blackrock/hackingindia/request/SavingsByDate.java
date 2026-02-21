package com.blackrock.hackingindia.request;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

public class SavingsByDate {
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime start;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime end;
    
    private BigDecimal amount;
    private BigDecimal profit;
    private BigDecimal taxBenefit;

    public LocalDateTime getStart() { return start; }
    public void setStart(LocalDateTime start) { this.start = start; }

    public LocalDateTime getEnd() { return end; }
    public void setEnd(LocalDateTime end) { this.end = end; }

    public BigDecimal getAmount() { return amount; }

    public void setAmount(BigDecimal amount) {

        this.amount = (amount != null) ? amount.setScale(1, RoundingMode.HALF_UP) : null;
    }

    public BigDecimal getProfit() { return profit; }

    public void setProfit(BigDecimal profit) {
        this.profit = (profit != null) ? profit.setScale(2, RoundingMode.HALF_UP) : null;
    }

    public BigDecimal getTaxBenefit() { return taxBenefit; }

    public void setTaxBenefit(BigDecimal taxBenefit) {
        this.taxBenefit = (taxBenefit != null) ? taxBenefit.setScale(2, RoundingMode.HALF_UP) : null;
    }
}
