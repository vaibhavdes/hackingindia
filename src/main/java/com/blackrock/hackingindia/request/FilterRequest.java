package com.blackrock.hackingindia.request;

import java.util.List;

import com.blackrock.hackingindia.pojo.RuleK;
import com.blackrock.hackingindia.pojo.RuleP;
import com.blackrock.hackingindia.pojo.RuleQ;
import com.blackrock.hackingindia.pojo.TransactionOutput;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

public record FilterRequest(
        List<@Valid RuleQ> q,
        List<@Valid RuleP> p,
        List<RuleK> k,   
        @Min(value = 0, message = "Wage cannot be negative") double wage,
        List<TransactionOutput> transactions
) {}