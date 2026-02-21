package com.blackrock.hackingindia.request;

import java.util.List;

import com.blackrock.hackingindia.pojo.RuleK;
import com.blackrock.hackingindia.pojo.RuleP;
import com.blackrock.hackingindia.pojo.RuleQ;
import com.blackrock.hackingindia.pojo.TransactionOutput;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;

public record ReturnsRequest(
    @Positive(message = "Age must be greater than zero") int age,
    @Min(value = 0, message = "Wage cannot be negative") double wage,
    @Min(value = 0, message = "Inflation cannot be negative") double inflation,
    List<@Valid RuleQ> q,
    List<@Valid RuleP> p,
    List<RuleK> k,
    List<TransactionOutput> transactions
) {}