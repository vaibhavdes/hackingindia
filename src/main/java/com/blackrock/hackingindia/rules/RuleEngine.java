package com.blackrock.hackingindia.rules;

import java.math.BigDecimal;
import java.util.List;

import com.blackrock.hackingindia.pojo.RuleK;
import com.blackrock.hackingindia.pojo.RuleP;
import com.blackrock.hackingindia.pojo.RuleQ;
import com.blackrock.hackingindia.utils.DateTimeUtils;

public class RuleEngine {

    public static BigDecimal fixedQRule(long transactionDate, List<RuleQ> qRules) {
        if (qRules == null || qRules.isEmpty()) {
            return null;
        }

        long latestStart = -1;
        BigDecimal qFixedAmountOverride = null;

        for (RuleQ qRule : qRules) {
            long start = DateTimeUtils.toEpochMillis(qRule.getStart());
            long end = DateTimeUtils.toEpochMillis(qRule.getEnd());

            if (transactionDate >= start && transactionDate <= end) {
                if (start > latestStart) {
                    latestStart = start;
                    qFixedAmountOverride = qRule.getFixed();
                }
            }
        }
        return qFixedAmountOverride;
    }

    public static BigDecimal checkAndCalculatePRule(long transactionDate, List<RuleP> pRules) {
        BigDecimal amountAddition = BigDecimal.ZERO;

        if (pRules == null || pRules.isEmpty()) {
            return amountAddition;
        }

        for (RuleP pRule : pRules) {
            long start = DateTimeUtils.toEpochMillis(pRule.getStart());
            long end = DateTimeUtils.toEpochMillis(pRule.getEnd());

            if (transactionDate >= start && transactionDate <= end) {
                amountAddition = amountAddition.add(pRule.getExtra());
            }
        }
        return amountAddition;
    }

    public static boolean checkIfKRule(long transactionDate, List<RuleK> kRules) {
        if (kRules == null || kRules.isEmpty()) {
            return false;
        }

        for (RuleK kRule : kRules) {
            long start = DateTimeUtils.toEpochMillis(kRule.getStart());
            long end = DateTimeUtils.toEpochMillis(kRule.getEnd());

            if (transactionDate >= start && transactionDate <= end) {
                return true;
            }
        }
        return false;
    }
}
