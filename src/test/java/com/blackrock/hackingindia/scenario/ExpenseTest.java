package com.blackrock.hackingindia.scenario;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.blackrock.hackingindia.pojo.Transaction;
import com.blackrock.hackingindia.rules.RuleEngine;
import com.blackrock.hackingindia.pojo.Result;
import com.blackrock.hackingindia.pojo.RuleK;
import com.blackrock.hackingindia.pojo.RuleP;
import com.blackrock.hackingindia.pojo.RuleQ;
import com.blackrock.hackingindia.utils.DateTimeUtils;
import com.blackrock.hackingindia.utils.NumberUtils;

public class ExpenseTest {

        List<Transaction> expenses = Arrays.asList(
                new Transaction(LocalDateTime.of(2023, 10, 12, 20, 15), new BigDecimal("250")),
                new Transaction(LocalDateTime.of(2023, 2, 28, 15, 49), new BigDecimal("375")),
                new Transaction(LocalDateTime.of(2023, 7, 1, 21, 59), new BigDecimal("620")),
                new Transaction(LocalDateTime.of(2023, 12, 17, 8, 9), new BigDecimal("480"))
        );

    @Test
    void testExpensesInvestmentSummary() {

        BigDecimal[] expectedCeilings = {
                new BigDecimal("300"),
                new BigDecimal("400"),
                new BigDecimal("700"),
                new BigDecimal("500")
        };

        BigDecimal[] expectedRemanents = {
                new BigDecimal("50"),
                new BigDecimal("25"),
                new BigDecimal("80"),
                new BigDecimal("20")
        };

        BigDecimal totalInvested = BigDecimal.ZERO;

        for (int i = 0; i < expenses.size(); i++) {
            Transaction expense = expenses.get(i);
            Result result = NumberUtils.calculateCeilingAndRemanent(expense.getAmount());

            assertEquals(expectedCeilings[i], result.getCeiling(),
                    "Ceiling mismatch " + expense);

            assertEquals(expectedRemanents[i], result.getRemanent(),
                    "Remanent mismatch " + expense);

            totalInvested = totalInvested.add(result.getRemanent());
        }

        assertEquals(new BigDecimal("175"), totalInvested,
                "Total invested");
    }

    @Test
    void testExpensesWithRuleQ() {

        RuleQ ruleQ = new RuleQ();
        ruleQ.setFixed(BigDecimal.ZERO);
        ruleQ.setStart(LocalDateTime.of(2023, 7, 1, 0, 0));
        ruleQ.setEnd(LocalDateTime.of(2023, 7, 31, 23, 59));
        
        List<RuleQ> qRules = Arrays.asList(ruleQ);

        BigDecimal[] expectedRemanents = {
                new BigDecimal("50"), 
                new BigDecimal("25"),
                new BigDecimal("0"), 
                new BigDecimal("20")
        };

        BigDecimal totalInvested = BigDecimal.ZERO;

        for (int i = 0; i < expenses.size(); i++) {
            Transaction expense = expenses.get(i);
            
            Result result = NumberUtils.calculateCeilingAndRemanent(expense.getAmount());
            BigDecimal finalRemanent = result.getRemanent();

            long receiptTime = DateTimeUtils.toEpochMillis(expense.getDate());
            BigDecimal qOverride = RuleEngine.fixedQRule(receiptTime, qRules);
            
            if (qOverride != null) {
                finalRemanent = qOverride;
            }

            assertEquals(expectedRemanents[i], finalRemanent,
                    "mismatch for expense " + expense.getDate());

            totalInvested = totalInvested.add(finalRemanent);
        }

        assertEquals(new BigDecimal("95"), totalInvested,
                "Total Invested Amount");
    }

    @Test
    void testExpensesWithRuleP() {

        RuleP ruleP = new RuleP();
        ruleP.setExtra(new BigDecimal("25"));
        ruleP.setStart(LocalDateTime.of(2023, 10, 1, 8, 0));
        ruleP.setEnd(LocalDateTime.of(2023, 12, 31, 19, 59));
        
        List<RuleP> pRules = Arrays.asList(ruleP);

        BigDecimal[] expectedRemanents = {
                new BigDecimal("75"), 
                new BigDecimal("25"), 
                new BigDecimal("80"), 
                new BigDecimal("45")  
        };

        for (int i = 0; i < expenses.size(); i++) {
            Transaction expense = expenses.get(i);
            long receiptTime = DateTimeUtils.toEpochMillis(expense.getDate());
            
            Result result = NumberUtils.calculateCeilingAndRemanent(expense.getAmount());
            BigDecimal finalRemanent = result.getRemanent();

            BigDecimal pBoost = RuleEngine.checkAndCalculatePRule(receiptTime, pRules);
            finalRemanent = finalRemanent.add(pBoost); 

            assertEquals(expectedRemanents[i], finalRemanent,
                    "Remanent mismatch for expense " + expense.getDate());
        }
    }

    @Test
    void testExpensesWithRuleK() {

        RuleK ruleKForMonth = new RuleK();
        ruleKForMonth.setStart(LocalDateTime.of(2023, 3, 1, 0, 0));
        ruleKForMonth.setEnd(LocalDateTime.of(2023, 11, 30, 23, 59));

        RuleK ruleKFullYear = new RuleK();
        ruleKFullYear.setStart(LocalDateTime.of(2023, 1, 1, 0, 0));
        ruleKFullYear.setEnd(LocalDateTime.of(2023, 12, 31, 23, 59));

        List<RuleK> kRules = Arrays.asList(ruleKForMonth, ruleKFullYear);

        BigDecimal marchToNovTotal = BigDecimal.ZERO;
        BigDecimal fullYearTotal = BigDecimal.ZERO;

        for (int i = 0; i < expenses.size(); i++) {
            Transaction expense = expenses.get(i);
            long receiptTime = DateTimeUtils.toEpochMillis(expense.getDate());
            
            Result result = NumberUtils.calculateCeilingAndRemanent(expense.getAmount());
            BigDecimal normalChange = result.getRemanent();

            long bank1Start = DateTimeUtils.toEpochMillis(kRules.get(0).getStart());
            long bank1End = DateTimeUtils.toEpochMillis(kRules.get(0).getEnd());
            
            if (receiptTime >= bank1Start && receiptTime <= bank1End) {
                marchToNovTotal = marchToNovTotal.add(normalChange);
            }

            long bank2Start = DateTimeUtils.toEpochMillis(kRules.get(1).getStart());
            long bank2End = DateTimeUtils.toEpochMillis(kRules.get(1).getEnd());
            
            if (receiptTime >= bank2Start && receiptTime <= bank2End) {
                fullYearTotal = fullYearTotal.add(normalChange);
            }
        }

        assertEquals(new BigDecimal("130"), marchToNovTotal,
                "The Rule K counted the wrong amount!");

        assertEquals(new BigDecimal("175"), fullYearTotal,
                "The Rule K counted the wrong amount!");
    }

    @Test
    void testAllRulesCombinedAsPerJudgesExample() {

        // Q Rule
        RuleQ ruleQ = new RuleQ();
        ruleQ.setFixed(BigDecimal.ZERO);
        ruleQ.setStart(LocalDateTime.of(2023, 7, 1, 0, 0));
        ruleQ.setEnd(LocalDateTime.of(2023, 7, 31, 23, 59));
        List<RuleQ> qRules = Arrays.asList(ruleQ);

        // P Rule
        RuleP ruleP = new RuleP();
        ruleP.setExtra(new BigDecimal("25"));
        ruleP.setStart(LocalDateTime.of(2023, 10, 1, 8, 0));
        ruleP.setEnd(LocalDateTime.of(2023, 12, 31, 19, 59));
        List<RuleP> pRules = Arrays.asList(ruleP);

        // 3. K Rule
        RuleK ruleKForMonth = new RuleK();
        ruleKForMonth.setStart(LocalDateTime.of(2023, 3, 1, 0, 0));
        ruleKForMonth.setEnd(LocalDateTime.of(2023, 11, 30, 23, 59));

        RuleK ruleKFullYear = new RuleK();
        ruleKFullYear.setStart(LocalDateTime.of(2023, 1, 1, 0, 0));
        ruleKFullYear.setEnd(LocalDateTime.of(2023, 12, 31, 23, 59));
        List<RuleK> kRules = Arrays.asList(ruleKForMonth, ruleKFullYear);

        BigDecimal marchToNovTotal = BigDecimal.ZERO;
        BigDecimal fullYearTotal = BigDecimal.ZERO;

        for (Transaction expense : expenses) {
            long receiptTime = DateTimeUtils.toEpochMillis(expense.getDate());
            
            Result result = NumberUtils.calculateCeilingAndRemanent(expense.getAmount());
            BigDecimal finalRemanent = result.getRemanent();

            BigDecimal qOverride = RuleEngine.fixedQRule(receiptTime, qRules);
            if (qOverride != null) {
                finalRemanent = qOverride;
            }

            BigDecimal pBoost = RuleEngine.checkAndCalculatePRule(receiptTime, pRules);
            finalRemanent = finalRemanent.add(pBoost); 

            long bank1Start = DateTimeUtils.toEpochMillis(kRules.get(0).getStart());
            long bank1End = DateTimeUtils.toEpochMillis(kRules.get(0).getEnd());
            if (receiptTime >= bank1Start && receiptTime <= bank1End) {
                marchToNovTotal = marchToNovTotal.add(finalRemanent);
            }

            long bank2Start = DateTimeUtils.toEpochMillis(kRules.get(1).getStart());
            long bank2End = DateTimeUtils.toEpochMillis(kRules.get(1).getEnd());
            if (receiptTime >= bank2Start && receiptTime <= bank2End) {
                fullYearTotal = fullYearTotal.add(finalRemanent);
            }
        }

        assertEquals(new BigDecimal("75"), marchToNovTotal,
                "The March-Nov Piggy Bank does not match the judges' final output!");

        assertEquals(new BigDecimal("145"), fullYearTotal,
                "The Full Year Piggy Bank does not match the judges' final output!");
    }
}
