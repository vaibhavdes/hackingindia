package com.blackrock.hackingindia.request;

import java.math.BigDecimal;
import java.util.List;

import com.blackrock.hackingindia.pojo.RuleK;
import com.blackrock.hackingindia.pojo.RuleP;
import com.blackrock.hackingindia.pojo.RuleQ;
import com.blackrock.hackingindia.pojo.TransactionOutput;

public record FilterRequest(
        List<RuleQ> q,
        List<RuleP> p,
        List<RuleK> k,   
        BigDecimal wage,
        List<TransactionOutput> transactions
) {}