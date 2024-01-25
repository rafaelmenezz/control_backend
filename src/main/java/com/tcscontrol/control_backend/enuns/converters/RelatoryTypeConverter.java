package com.tcscontrol.control_backend.enuns.converters;

import java.util.stream.Stream;

import com.tcscontrol.control_backend.enuns.RelatoryType;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class RelatoryTypeConverter implements AttributeConverter<RelatoryType, String> {

    @Override
    public String convertToDatabaseColumn(RelatoryType attribute) {
        if(attribute == null){
            return null;
        }
        return attribute.getValue();
    }

    @Override
    public RelatoryType convertToEntityAttribute(String dbData) {
          if(dbData == null){
            return null;
        }

        return Stream.of(RelatoryType.values())
        .filter(c -> c.getValue().equals(dbData))
        .findFirst()
        .orElseThrow(IllegalArgumentException::new);
    }
    
}
