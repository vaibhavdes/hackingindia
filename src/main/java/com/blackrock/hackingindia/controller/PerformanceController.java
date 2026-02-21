package com.blackrock.hackingindia.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blackrock.hackingindia.service.PerformanceService;

@RestController
@RequestMapping("/blackrock/challenge/v1")
public class PerformanceController {

    @Autowired
    PerformanceService performanceService;

    @GetMapping("/performance")
    public ResponseEntity<?> validateTransactions() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(performanceService.getSystemMetrics());
    }
}
