
package com.tk.batch.data_processing_batch.config;

import com.tk.batch.data_processing_batch.model.ServerMetric;
import com.tk.batch.data_processing_batch.model.MetricSummary;
import com.tk.batch.data_processing_batch.model.VehicleMapping;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.json.JacksonJsonObjectReader;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.json.builder.JsonItemReaderBuilder;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class BatchConfig {
    @Bean
    public Job analyzeVehicleMappingJob(JobRepository jobRepository, Step vehicleMappingStep) {
        return new JobBuilder("analyzeVehicleMappingJob", jobRepository)
                .start(vehicleMappingStep)
                .build();
    }

    @Bean
    public Step vehicleMappingStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("vehicleMappingStep", jobRepository)
                .<VehicleMapping, VehicleMapping>chunk(10, transactionManager)
                .reader(new JsonItemReaderBuilder<VehicleMapping>()
                        .jsonObjectReader(new JacksonJsonObjectReader<>(VehicleMapping.class))
                        .resource(new ClassPathResource("dealer_logs.json"))
                        .name("vehicleMappingReader")
                        .build())
                .processor(new VehicleMappingProcessor())
                .writer(new FlatFileItemWriterBuilder<VehicleMapping>()
                        .name("vehicleMappingWriter")
                        .resource(new FileSystemResource("missing_vehicle_mappings.csv"))
                        .delimited()
                        .names("logId", "dealerId", "make", "model", "year", "message", "mapped")
                        .build())
                .build();
    }


    @Bean
    public DatadogLogReader reader() {
        return new DatadogLogReader(new ClassPathResource("datadog.log"));
    }

    @Bean
    public MetricProcessor processor() {
        return new MetricProcessor();
    }

    @Bean
    public FlatFileItemWriter<MetricSummary> writer() {
        return new FlatFileItemWriterBuilder<MetricSummary>()
                .name("summaryWriter")
                .resource(new FileSystemResource("metric_summary.csv"))
                .delimited()
                .names("serverId", "avgCpuUsage", "avgMemoryUsage", "totalRequests", "avgResponseTime")
                .build();
    }

    @Bean
    public Job processMetricsJob(JobRepository jobRepository, Step step1) {
        return new JobBuilder("processMetricsJob", jobRepository)
                .start(step1)
                .build();
    }

    @Bean
    public Step step1(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("step1", jobRepository)
                .<ServerMetric, MetricSummary>chunk(10, transactionManager)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .build();
    }
}
