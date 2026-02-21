package com.blackrock.hackingindia.request;

import java.math.BigDecimal;
import java.util.List;

import com.blackrock.hackingindia.pojo.TransactionOutput;

public record ValidatorRequest(
        BigDecimal wage,
        List<TransactionOutput> transactions
) {}