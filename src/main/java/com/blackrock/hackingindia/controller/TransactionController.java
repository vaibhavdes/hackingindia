package com.blackrock.hackingindia.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blackrock.hackingindia.pojo.Transaction;
import com.blackrock.hackingindia.request.FilterRequest;
import com.blackrock.hackingindia.request.ValidatorRequest;
import com.blackrock.hackingindia.service.TransactionProcessService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/blackrock/challenge/v1")
public class TransactionController {
    
    @Autowired
    TransactionProcessService expenseProcessService;
    
    @PostMapping("/transactions:parse")
    public ResponseEntity<?> parseTransactions(@RequestBody List<Transaction> expenses) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(expenseProcessService.parseTransaction(expenses));
    }

    @PostMapping("/transactions:validator")
    public ResponseEntity<?> validateTransactions(@Valid @RequestBody ValidatorRequest validateRequest) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(expenseProcessService.validateTransaction(validateRequest.transactions()));
    }

    @PostMapping("/transactions:filter")
    public ResponseEntity<?> filterTransactions(@Valid @RequestBody FilterRequest filterRequest) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(expenseProcessService.filterTransaction(filterRequest));
    }
}
