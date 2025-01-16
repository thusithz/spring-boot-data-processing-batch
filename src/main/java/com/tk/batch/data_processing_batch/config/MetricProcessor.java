package com.tk.batch.data_processing_batch.config;

import com.tk.batch.data_processing_batch.model.ServerMetric;
import com.tk.batch.data_processing_batch.model.MetricSummary;
import org.springframework.batch.item.ItemProcessor;

public class MetricProcessor implements ItemProcessor<ServerMetric, MetricSummary> {

    @Override
    public MetricSummary process(ServerMetric metric) {
        MetricSummary summary = new MetricSummary();
        summary.setServerId(metric.getServerId());
        summary.setAvgCpuUsage(metric.getCpuUsage());
        summary.setAvgMemoryUsage(metric.getMemoryUsage());
        summary.setTotalRequests(metric.getRequestCount());
        summary.setAvgResponseTime(metric.getResponseTime());

        // Add custom processing logic here
        if (metric.getCpuUsage() > 90.0 || metric.getMemoryUsage() > 85.0) {
            System.out.println("High resource usage detected for server: " + metric.getServerId());
        }

        return summary;
    }
}
