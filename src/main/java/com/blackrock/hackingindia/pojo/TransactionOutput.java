package com.blackrock.hackingindia.pojo;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransactionOutput {

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime date;
    private BigDecimal amount;
    private BigDecimal ceiling;
    private BigDecimal remanent;

    private String message;
    private Boolean inKPeriod;

    @com.fasterxml.jackson.annotation.JsonIgnore
    private boolean validFlag;

    public TransactionOutput() {}

    public TransactionOutput(LocalDateTime date, BigDecimal amount) {
        this.date = date;
        this.amount = amount;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getCeiling() {
        return ceiling;
    }

    public void setCeiling(BigDecimal ceiling) {
        this.ceiling = ceiling;
    }

    public BigDecimal getRemanent() {
        return remanent;
    }

    public void setRemanent(BigDecimal remanent) {
        this.remanent = remanent;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getInKPeriod() {
        return inKPeriod;
    }

    public void setInKPeriod(Boolean inKPeriod) {
        this.inKPeriod = inKPeriod;
    }

    public boolean isValidFlag() {
        return validFlag;
    }

    public void setValidFlag(boolean validFlag) {
        this.validFlag = validFlag;
    }
}
