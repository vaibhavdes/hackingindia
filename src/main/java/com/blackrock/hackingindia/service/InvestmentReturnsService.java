package com.blackrock.hackingindia.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blackrock.hackingindia.config.InvestmentType;
import com.blackrock.hackingindia.pojo.RuleK;
import com.blackrock.hackingindia.pojo.TransactionOutput;
import com.blackrock.hackingindia.request.FilterRequest;
import com.blackrock.hackingindia.request.ReturnsRequest;
import com.blackrock.hackingindia.request.SavingsByDate;
import com.blackrock.hackingindia.response.ReturnsResponse;
import com.blackrock.hackingindia.utils.DateTimeUtils;
import com.blackrock.hackingindia.utils.InvestmentUtil;
import com.blackrock.hackingindia.utils.TaxUtil;

@Service
public class InvestmentReturnsService {
    
    @Autowired
    TransactionProcessService transactionProcessService;

    public ReturnsResponse calculateReturns(ReturnsRequest request, InvestmentType investmentType) {

        FilterRequest filterReq = new FilterRequest(request.q(), request.p(), request.k(), request.wage(), request.transactions());
        Map<String, List<TransactionOutput>> result = transactionProcessService.filterTransaction(filterReq);

        List<TransactionOutput> validReceipts = result.get("valid");

        BigDecimal totalAmount = validReceipts.stream()
                .map(TransactionOutput::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalCeiling = validReceipts.stream()
                .map(TransactionOutput::getCeiling)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        List<SavingsByDate> savingsList = new ArrayList<>();

        if (request.k() != null) {
            for (RuleK kRule : request.k()) {

                double total = 0.0;
                long kStart = DateTimeUtils.toEpochMillis(kRule.getStart());
                long kEnd = DateTimeUtils.toEpochMillis(kRule.getEnd());

                for (TransactionOutput receipt : validReceipts) {
                    long receiptTime = DateTimeUtils.toEpochMillis(receipt.getDate());
                    if (receiptTime >= kStart && receiptTime <= kEnd) {
                        total += receipt.getRemanent().doubleValue();
                    }
                }

                double rate = investmentType.getAnnualRate();
                BigDecimal profit = InvestmentUtil.calculateProfit(total, rate, request.age(), request.inflation());
                
                BigDecimal taxBenefit = BigDecimal.ZERO;
                if (investmentType.givesTaxBenefit() && total > 0) {
                    taxBenefit = TaxUtil.calculateNpsTaxBenefit(request.wage(), total);
                }

                SavingsByDate saving = new SavingsByDate();
                saving.setStart(kRule.getStart());
                saving.setEnd(kRule.getEnd());
                saving.setAmount(BigDecimal.valueOf(total).setScale(1, RoundingMode.HALF_UP));
                saving.setProfit(profit);
                saving.setTaxBenefit(taxBenefit);
                
                savingsList.add(saving);
            }
        }
        
        ReturnsResponse response = new ReturnsResponse();
        response.setTotalTransactionAmount(totalAmount.setScale(1, RoundingMode.HALF_UP));
        response.setTotalCeiling(totalCeiling.setScale(1, RoundingMode.HALF_UP));
        response.setSavingsByDates(savingsList);

        return response;
    }

    public String bestInvestment(ReturnsRequest request) {
        
        Map<String, ReturnsResponse> allOffers = new HashMap<>();
        
        double biggestProfit = -1.0;
        String winningPlan = "";

        for (InvestmentType plan : InvestmentType.values()) {
            
            ReturnsResponse planResult = calculateReturns(request, plan);
            allOffers.put(plan.name(), planResult);

            double totalBenefitForThisPlan = 0.0;
            
            if (planResult.getSavingsByDates() != null) {
                for (SavingsByDate basket : planResult.getSavingsByDates()) {
                    totalBenefitForThisPlan += basket.getProfit().doubleValue();
                    totalBenefitForThisPlan += basket.getTaxBenefit().doubleValue();
                }
            }

            if (totalBenefitForThisPlan > biggestProfit) {
                biggestProfit = totalBenefitForThisPlan;
                winningPlan = plan.name();
            }
        }

        String suggestion = "Result : " + winningPlan + " " + "plan gives you the highest profit : " + biggestProfit ;
        return suggestion;
    }
}
