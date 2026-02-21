package com.blackrock.hackingindia.rules;

import java.math.BigDecimal;
import java.util.List;

import com.blackrock.hackingindia.pojo.RuleK;
import com.blackrock.hackingindia.pojo.RuleP;
import com.blackrock.hackingindia.pojo.RuleQ;
import com.blackrock.hackingindia.utils.DateTimeUtils;

public class RuleEngine {

    public static BigDecimal fixedQRule(long receiptTime, List<RuleQ> qRules) {
        if (qRules == null || qRules.isEmpty()) {
            return null;
        }

        long latestStart = -1;
        BigDecimal qOverride = null;

        for (RuleQ qRule : qRules) {
            long start = DateTimeUtils.toEpochMillis(qRule.getStart());
            long end = DateTimeUtils.toEpochMillis(qRule.getEnd());

            if (receiptTime >= start && receiptTime <= end) {
                if (start > latestStart) {
                    latestStart = start;
                    qOverride = qRule.getFixed();
                }
            }
        }
        return qOverride;
    }

    public static BigDecimal checkAndCalculatePRule(long receiptTime, List<RuleP> pRules) {
        BigDecimal totalBoost = BigDecimal.ZERO;

        if (pRules == null || pRules.isEmpty()) {
            return totalBoost;
        }

        for (RuleP pRule : pRules) {
            long start = DateTimeUtils.toEpochMillis(pRule.getStart());
            long end = DateTimeUtils.toEpochMillis(pRule.getEnd());

            if (receiptTime >= start && receiptTime <= end) {
                totalBoost = totalBoost.add(pRule.getExtra());
            }
        }
        return totalBoost;
    }

    public static boolean checkIfKRule(long receiptTime, List<RuleK> kRules) {
        if (kRules == null || kRules.isEmpty()) {
            return false;
        }

        for (RuleK kRule : kRules) {
            long start = DateTimeUtils.toEpochMillis(kRule.getStart());
            long end = DateTimeUtils.toEpochMillis(kRule.getEnd());

            if (receiptTime >= start && receiptTime <= end) {
                return true;
            }
        }
        return false;
    }
}
