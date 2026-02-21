package com.blackrock.hackingindia.response;

public record PerformanceResponse(
    String time, 
    String memory, 
    int threads
) {}