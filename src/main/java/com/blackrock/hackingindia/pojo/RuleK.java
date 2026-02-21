package com.blackrock.hackingindia.pojo;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.AssertTrue;

// (Evaluation Grouping)
public class RuleK {

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm[:ss]")
    private LocalDateTime start;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm[:ss]")
    private LocalDateTime end;
    public RuleK() {}

    public RuleK(LocalDateTime start, LocalDateTime end) {
        this.start = start;
        this.end = end;
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
