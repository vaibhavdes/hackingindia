package com.blackrock.hackingindia.pojo;

import java.math.BigDecimal;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public class RuleK {

    @NotNull(message = "Start must not be null")
    @PositiveOrZero(message = "Start must be zero or positive")
    private BigDecimal start;

    @NotNull(message = "End must not be null")
    @PositiveOrZero(message = "End must be zero or positive")
    private BigDecimal end;

    public RuleK() {}

    public RuleK(BigDecimal start, BigDecimal end) {
        this.start = start;
        this.end = end;
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
