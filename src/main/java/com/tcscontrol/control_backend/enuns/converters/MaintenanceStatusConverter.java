package com.tcscontrol.control_backend.enuns.converters;

import java.util.stream.Stream;

import com.tcscontrol.control_backend.enuns.MaintenanceStatus;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class MaintenanceStatusConverter implements AttributeConverter<MaintenanceStatus, String> {

    @Override
    public String convertToDatabaseColumn(MaintenanceStatus maintenanceStatus) {
        if(maintenanceStatus == null){
            return null;
        }
        return maintenanceStatus.getValue();
    }

    @Override
    public MaintenanceStatus convertToEntityAttribute(String value) {
        if(value == null){
            return null;
        }

        return Stream.of(MaintenanceStatus.values())
        .filter(c -> c.getValue().equals(value))
        .findFirst()
        .orElseThrow(IllegalArgumentException::new);
    }
    
}
