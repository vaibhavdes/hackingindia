package com.blackrock.hackingindia.service;

import java.lang.management.ManagementFactory;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Service;

import com.blackrock.hackingindia.response.PerformanceResponse;

@Service
public class PerformanceService {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS").withZone(ZoneId.of("UTC"));
    
    public PerformanceResponse getSystemMetrics() {

        long uptimeMillis = ManagementFactory.getRuntimeMXBean().getUptime();
        String formattedTime = FORMATTER.format(Instant.ofEpochMilli(uptimeMillis));

        long totalMemory = Runtime.getRuntime().totalMemory();
        long freeMemory = Runtime.getRuntime().freeMemory();
        long usedMemoryBytes = totalMemory - freeMemory;

        double usedMemoryMb = usedMemoryBytes / (1024.0 * 1024.0);
        String formattedMemory = String.format("%.2f MB", usedMemoryMb);

        int activeThreads = ManagementFactory.getThreadMXBean().getThreadCount();

        return new PerformanceResponse(formattedTime, formattedMemory, activeThreads);
    }
}
