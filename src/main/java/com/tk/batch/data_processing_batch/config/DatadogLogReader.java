package com.tk.batch.data_processing_batch.config;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tk.batch.data_processing_batch.model.ServerMetric;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.core.io.Resource;
import java.time.LocalDateTime;

public class DatadogLogReader extends FlatFileItemReader<ServerMetric> {
    private final ObjectMapper mapper = new ObjectMapper();

    public DatadogLogReader(Resource resource) {
        setResource(resource);
        setLineMapper(createLineMapper());
        setLinesToSkip(0);
    }

    private LineMapper<ServerMetric> createLineMapper() {
        return (line, lineNumber) -> {
            try {
                JsonNode root = mapper.readTree(line);

                ServerMetric metric = new ServerMetric();
                metric.setServerId(root.get("host").asText());
                metric.setTimestamp(LocalDateTime.parse(root.get("timestamp").asText().replace("Z", "")));

                JsonNode metrics = root.get("metrics");
                metric.setCpuUsage(metrics.get("cpu").asDouble());
                metric.setMemoryUsage(metrics.get("memory").asDouble());
                metric.setRequestCount(metrics.get("requests").asInt());
                metric.setResponseTime(metrics.get("response_time").asLong());

                return metric;
            } catch (Exception e) {
                throw new RuntimeException("Error parsing line " + lineNumber, e);
            }
        };
    }
}

