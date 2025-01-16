package com.tk.batch.data_processing_batch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DataProcessingBatchApplication {

	public static void main(String[] args) {
		SpringApplication.run(DataProcessingBatchApplication.class, args);
	}

}
