package com.blackrock.hackingindia.scenario;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.blackrock.hackingindia.pojo.Expense;
import com.blackrock.hackingindia.pojo.Result;
import com.blackrock.hackingindia.utils.NumberUtils;

public class ExpenseTest {

    @Test
    void testExpensesInvestmentSummary() {

        List<Expense> expenses = Arrays.asList(
                new Expense(LocalDateTime.of(2023, 10, 12, 20, 15), new BigDecimal("250")),
                new Expense(LocalDateTime.of(2023, 2, 28, 15, 49), new BigDecimal("375")),
                new Expense(LocalDateTime.of(2023, 7, 1, 21, 59), new BigDecimal("620")),
                new Expense(LocalDateTime.of(2023, 12, 17, 8, 9), new BigDecimal("480"))
        );

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

        BigDecimal totalInvestable = BigDecimal.ZERO;

        for (int i = 0; i < expenses.size(); i++) {
            Expense expense = expenses.get(i);
            Result result = NumberUtils.calculateCeilingAndRemanent(expense.getAmount());

            assertEquals(expectedCeilings[i], result.getCeiling(),
                    "Ceiling mismatch for expense " + expense);

            assertEquals(expectedRemanents[i], result.getRemanent(),
                    "Remanent mismatch for expense " + expense);

            totalInvestable = totalInvestable.add(result.getRemanent());
        }

        assertEquals(new BigDecimal("175"), totalInvestable,
                "Total investable amount mismatch");
    }
}
