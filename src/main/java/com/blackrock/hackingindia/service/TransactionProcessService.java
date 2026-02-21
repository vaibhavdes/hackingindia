package com.blackrock.hackingindia.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.blackrock.hackingindia.exceptions.NegativeNumberException;
import com.blackrock.hackingindia.pojo.Result;
import com.blackrock.hackingindia.pojo.RuleK;
import com.blackrock.hackingindia.pojo.RuleP;
import com.blackrock.hackingindia.pojo.RuleQ;
import com.blackrock.hackingindia.pojo.Transaction;
import com.blackrock.hackingindia.pojo.TransactionOutput;
import com.blackrock.hackingindia.request.FilterRequest;
import com.blackrock.hackingindia.rules.RuleEngine;
import com.blackrock.hackingindia.utils.DateTimeUtils;
import com.blackrock.hackingindia.utils.NumberUtils;

@Service
public class TransactionProcessService {
    
    public List<TransactionOutput> parseTransaction(List<Transaction> expenseTransactions){

        List<TransactionOutput> parseResult  = expenseTransactions.parallelStream().map(expense -> {

                TransactionOutput output = new TransactionOutput(expense.getDate(), expense.getAmount());

                Result mathResult = NumberUtils.calculateCeilingAndRemanent(expense.getAmount());
                output.setCeiling(mathResult.getCeiling());
                output.setRemanent(mathResult.getRemanent());
                try{
                    NumberUtils.checkIfNegative(expense.getAmount());
                } catch (NegativeNumberException e) {
                    output.setMessage(e.getMessage()); 
                }
                return output;
            }).collect(Collectors.toList());
        return parseResult;
    }

    public Map<String, List<TransactionOutput>> validateTransaction(List<TransactionOutput> expenseTransactions){
        Set<String> duplicateTransaction = ConcurrentHashMap.newKeySet();

        Map<Boolean, List<TransactionOutput>> result  = expenseTransactions.parallelStream().map(expense -> {
            
                TransactionOutput output = new TransactionOutput(expense.getDate(), expense.getAmount());
                output.setCeiling(expense.getCeiling());
                output.setRemanent(expense.getRemanent());

                try {
                    NumberUtils.checkIfNegative(expense.getAmount());
                } catch (NegativeNumberException e) {
                    output.setMessage(e.getMessage()); 
                    output.setValidFlag(false);
                    return output;
                }

                String uniqueKey = expense.getDate() + "_" + expense.getAmount().toString();
                boolean isNewReceipt = duplicateTransaction.add(uniqueKey);

                if (!isNewReceipt) {
                    output.setMessage("Duplicate transaction");
                    output.setValidFlag(false);
                    return output;
                }

                output.setValidFlag(true);
                            
                return output;
            }).collect(Collectors.partitioningBy(TransactionOutput::isValidFlag));

        Map<String, List<TransactionOutput>> validateResult = new HashMap<>();
        validateResult.put("valid", result.get(true));
        validateResult.put("invalid", result.get(false));

        return validateResult;
    }

    public Map<String, List<TransactionOutput>>filterTransaction(FilterRequest filterRequest){

        Map<String, List<TransactionOutput>> result = validateTransaction(filterRequest.transactions());

        List<TransactionOutput> validReceipts = result.get("valid");

        validReceipts.parallelStream().forEach(expense -> {

            long receiptTime = DateTimeUtils.toEpochMillis(expense.getDate());

            Result mathResult = NumberUtils.calculateCeilingAndRemanent(expense.getAmount());
            BigDecimal finalCeiling = mathResult.getCeiling();
            BigDecimal finalRemanent = mathResult.getRemanent();

            BigDecimal qRuleCheck = RuleEngine.fixedQRule(receiptTime, filterRequest.q());
            if (qRuleCheck != null) {
                finalRemanent = qRuleCheck;
            }
            
            // if (filterRequest.q() != null) {
            //     long latestStart = -1;
            //     BigDecimal qOverride = null;

            //     for (RuleQ qRule : filterRequest.q()) {
            //         long start = DateTimeUtils.toEpochMillis(qRule.getStart());
            //         long end = DateTimeUtils.toEpochMillis(qRule.getEnd());

            //         if (receiptTime >= start && receiptTime <= end) {
            //             if (start > latestStart) {
            //                 latestStart = start;
            //                 qOverride = qRule.getFixed();
            //             }
            //         }
            //     }
            //     if (qOverride != null) {
            //         finalRemanent = qOverride; 
            //     }
            // }

            BigDecimal pRuleAmount = RuleEngine.checkAndCalculatePRule(receiptTime, filterRequest.p());
            finalRemanent = finalRemanent.add(pRuleAmount);
            // if (filterRequest.p() != null) {
            //     for (RuleP pRule : filterRequest.p()) {
            //         long start = DateTimeUtils.toEpochMillis(pRule.getStart());
            //         long end = DateTimeUtils.toEpochMillis(pRule.getEnd());

            //         if (receiptTime >= start && receiptTime <= end) {
            //             finalRemanent = finalRemanent.add(pRule.getExtra());
            //         }
            //     }
            // }

            boolean belongsInBasket = RuleEngine.checkIfKRule(receiptTime, filterRequest.k());

            // boolean belongsInKRule = false;
            // if (filterRequest.k() != null) {
            //     for (RuleK kRule : filterRequest.k()) {
            //         long start = DateTimeUtils.toEpochMillis(kRule.getStart());
            //         long end = DateTimeUtils.toEpochMillis(kRule.getEnd());

            //         if (receiptTime >= start && receiptTime <= end) {
            //             belongsInKRule = true;
            //             break;
            //         }
            //     }
            // }

            expense.setCeiling(finalCeiling);
            expense.setRemanent(finalRemanent);
            expense.setInKPeriod(belongsInBasket);

        });

        return result;
    }
}
