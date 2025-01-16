package com.tk.batch.data_processing_batch.model;

import lombok.Data;

@Data
public class MetricSummary {
    private String serverId;
    private double avgCpuUsage;
    private double avgMemoryUsage;
    private int totalRequests;
    private double avgResponseTime;
}

