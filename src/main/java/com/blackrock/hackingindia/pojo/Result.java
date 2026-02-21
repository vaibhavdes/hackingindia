package com.blackrock.hackingindia.pojo;

import java.math.BigDecimal;

public class Result {
    
    private final BigDecimal ceiling;
    private final BigDecimal remanent;

    public Result(BigDecimal ceiling, BigDecimal remanent) {
        this.ceiling = ceiling;
        this.remanent = remanent;
    }

    public BigDecimal getCeiling() {
        return ceiling;
    }

    public BigDecimal getRemanent() {
        return remanent;
    }
}
