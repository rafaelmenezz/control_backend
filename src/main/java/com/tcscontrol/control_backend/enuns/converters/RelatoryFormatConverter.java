package com.tcscontrol.control_backend.enuns.converters;

import java.util.stream.Stream;
import com.tcscontrol.control_backend.enuns.RelatoryFormatType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class RelatoryFormatConverter implements AttributeConverter<RelatoryFormatType, String> {

    @Override
    public String convertToDatabaseColumn(RelatoryFormatType attribute) {
        if(attribute == null){
            return null;
        }
        return attribute.getValue();
    }

    @Override
    public RelatoryFormatType convertToEntityAttribute(String dbData) {
         if(dbData == null){
            return null;
        }

        return Stream.of(RelatoryFormatType.values())
        .filter(c -> c.getValue().equals(dbData))
        .findFirst()
        .orElseThrow(IllegalArgumentException::new);
    }


    
}
