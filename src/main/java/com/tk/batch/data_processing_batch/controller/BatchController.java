package com.tk.batch.data_processing_batch.controller;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
public class BatchController {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job processMetricsJob;

    @Autowired
    private Job analyzeVehicleMappingJob;

    @PostMapping("/analyze-vehicle-mapping")
    public String analyzeVehicleMapping() throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis())
                .toJobParameters();

        jobLauncher.run(analyzeVehicleMappingJob, jobParameters);
        return "Vehicle mapping analysis started";
    }


    @PostMapping("/start-batch")
    public String startBatch() throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis())
                .toJobParameters();

        jobLauncher.run(processMetricsJob, jobParameters);
        return "Batch job started";
    }
}