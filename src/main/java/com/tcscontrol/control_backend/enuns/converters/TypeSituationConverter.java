package com.tcscontrol.control_backend.enuns.converters;

import java.util.stream.Stream;
import com.tcscontrol.control_backend.enuns.TypeSituation;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class TypeSituationConverter implements AttributeConverter<TypeSituation, String>{

    @Override
    public String convertToDatabaseColumn(TypeSituation typeSituation) {
      if (typeSituation == null) {
        return null;
      }
      return typeSituation.getValue();
    }

    @Override
    public TypeSituation convertToEntityAttribute(String value) {
        if(value == null){
            return null;
        }
        return Stream.of(TypeSituation.values())
        .filter(c -> c.getValue().equals(value))
        .findFirst()
        .orElseThrow(IllegalArgumentException::new);
    }
}