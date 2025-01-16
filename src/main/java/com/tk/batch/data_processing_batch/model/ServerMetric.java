package com.tk.batch.data_processing_batch.model;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ServerMetric {
    private String serverId;
    private LocalDateTime timestamp;
    private double cpuUsage;
    private double memoryUsage;
    private int requestCount;
    private long responseTime;
}

