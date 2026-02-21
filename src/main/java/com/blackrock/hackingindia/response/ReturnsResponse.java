package com.blackrock.hackingindia.response;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import com.blackrock.hackingindia.request.SavingsByDate;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReturnsResponse {

    private BigDecimal totalTransactionAmount;
    private BigDecimal totalCeiling;
    private List<SavingsByDate> savingsByDates;
    
    public BigDecimal getTotalTransactionAmount() { return totalTransactionAmount; }
    public void setTotalTransactionAmount(BigDecimal totalTransactionAmount) {
        this.totalTransactionAmount = (totalTransactionAmount != null) ? totalTransactionAmount.setScale(1, RoundingMode.HALF_UP) : null;
    }

    public BigDecimal getTotalCeiling() { return totalCeiling; }
    public void setTotalCeiling(BigDecimal totalCeiling) {
        this.totalCeiling = (totalCeiling != null) ? totalCeiling.setScale(1, RoundingMode.HALF_UP) : null;
    }

    public List<SavingsByDate> getSavingsByDates() { return savingsByDates; }

    public void setSavingsByDates(List<SavingsByDate> savingsByDates) { this.savingsByDates = savingsByDates; }
}
