package com.blackrock.hackingindia.pojo;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

// concerned about your future, you add an extra amount to the amounts to be invested
// (Extra Amount Addition)
public class RuleP {
    
    @NotNull(message = "Extra value must not be null")
    @PositiveOrZero(message = "Extra must be zero or positive")
    private BigDecimal extra;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm[:ss]")
    private LocalDateTime start;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm[:ss]")
    private LocalDateTime end;

    public RuleP() {}

    public RuleP(BigDecimal extra, LocalDateTime start, LocalDateTime end) {
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

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
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
