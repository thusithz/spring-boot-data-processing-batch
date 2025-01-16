package com.tk.batch.data_processing_batch.model;

import lombok.Data;

@Data
public class VehicleMapping {
    private String logId;
    private String dealerId;
    private String make;
    private String model;
    private String year;
    private String message;
    private boolean isMapped;
}