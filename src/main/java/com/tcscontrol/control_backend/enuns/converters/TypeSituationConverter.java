package com.tcscontrol.control_backend.enuns.converters;

import java.util.stream.Stream;
import com.tcscontrol.control_backend.enuns.SituationType;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class TypeSituationConverter implements AttributeConverter<SituationType, String>{

    @Override
    public String convertToDatabaseColumn(SituationType situationType) {
      if (situationType == null) {
        return null;
      }
      return situationType.getValue();
    }

    @Override
    public SituationType convertToEntityAttribute(String value) {
        if(value == null){
            return null;
        }
        return Stream.of(SituationType.values())
        .filter(c -> c.getValue().equals(value))
        .findFirst()
        .orElseThrow(IllegalArgumentException::new);
    }
    
}
