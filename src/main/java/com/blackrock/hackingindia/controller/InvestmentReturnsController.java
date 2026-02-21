package com.blackrock.hackingindia.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blackrock.hackingindia.config.InvestmentType;
import com.blackrock.hackingindia.request.ReturnsRequest;
import com.blackrock.hackingindia.response.ReturnsResponse;
import com.blackrock.hackingindia.service.InvestmentReturnsService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/blackrock/challenge/v1")
public class InvestmentReturnsController {
    
    @Autowired
    InvestmentReturnsService investmentReturnsService;

    @PostMapping("/returns:nps")
    public ResponseEntity<ReturnsResponse> calculateNpsReturns(@Valid @RequestBody ReturnsRequest request) {
        return ResponseEntity.ok(investmentReturnsService.calculateReturns(request, InvestmentType.NPS));
    }

    @PostMapping("/returns:index")
    public ResponseEntity<ReturnsResponse> calculateIndexReturns(@Valid @RequestBody ReturnsRequest request) {
        return ResponseEntity.ok(investmentReturnsService.calculateReturns(request, InvestmentType.INDEX));
    }

    @PostMapping("/returns:fd")
    public ResponseEntity<ReturnsResponse> calculateFDReturns(@Valid @RequestBody ReturnsRequest request) {
        return ResponseEntity.ok(investmentReturnsService.calculateReturns(request, InvestmentType.FIXED_DEPOSIT));
    }

    @PostMapping("/returns:recommend")
    public ResponseEntity<String> suggestBest(@Valid @RequestBody ReturnsRequest request) {
        return ResponseEntity.ok(investmentReturnsService.bestInvestment(request));
    }
}
