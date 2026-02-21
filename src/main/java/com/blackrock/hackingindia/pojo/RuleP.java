package com.blackrock.hackingindia.pojo;

import java.math.BigDecimal;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public class RuleP {
    
    @NotNull(message = "Extra value must not be null")
    @PositiveOrZero(message = "Extra must be zero or positive")
    private BigDecimal extra;

    @NotNull(message = "Start must not be null")
    @PositiveOrZero(message = "Start must be zero or positive")
    private BigDecimal start;

    @NotNull(message = "End must not be null")
    @PositiveOrZero(message = "End must be zero or positive")
    private BigDecimal end;

    public RuleP() {}

    public RuleP(BigDecimal extra, BigDecimal start, BigDecimal end) {
        this.extra = extra;
        this.start = start;
        this.end = end;
    }

    public BigDecimal getExtra() {
        return extra;
    }

    public void setExtra(BigDecimal extra) {
        this.extra = extra;
    }

    public BigDecimal getStart() {
        return start;
    }

    public void setStart(BigDecimal start) {
        this.start = start;
    }

    public BigDecimal getEnd() {
        return end;
    }

    public void setEnd(BigDecimal end) {
        this.end = end;
    }

    @AssertTrue(message = "Start must be less than or equal to End")
    public boolean isValidRange() {
        if (start == null || end == null) {
            return true;
        }
        return start.compareTo(end) <= 0;
    }
}
