package com.blackrock.hackingindia.pojo;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

// recorded during the year when you can only invest a fixed amount
public class RuleQ {

    @NotNull(message = "Fixed value must not be null")
    @PositiveOrZero(message = "Fixed value must be zero or positive")
    private BigDecimal fixed;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm[:ss]")
    private LocalDateTime start;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm[:ss]")
    private LocalDateTime end;

    public RuleQ() {}

    public RuleQ(BigDecimal fixed, LocalDateTime start, LocalDateTime end) {
        this.fixed = fixed;
        this.start = start;
        this.end = end;
    }

        public BigDecimal getFixed() {
        return fixed;
    }

    public void setFixed(BigDecimal fixed) {
        this.fixed = fixed;
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
