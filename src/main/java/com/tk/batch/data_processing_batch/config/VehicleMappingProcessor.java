package com.tk.batch.data_processing_batch.config;

import com.tk.batch.data_processing_batch.model.VehicleMapping;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class VehicleMappingProcessor implements ItemProcessor<VehicleMapping, VehicleMapping> {

    @Override
    public VehicleMapping process(final VehicleMapping mapping) throws Exception {
        if (!mapping.isMapped()) {
            mapping.setMessage("Vehicle mapping needs to be processed");
            return mapping;
        }
        return null;
    }
}
