package com.tcscontrol.control_backend.enuns.converters;

import java.util.stream.Stream;

import com.tcscontrol.control_backend.enuns.TypeWarranty;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class TypeWarrantyConverter implements AttributeConverter<TypeWarranty, String> {

    @Override
    public String convertToDatabaseColumn(TypeWarranty typeWarranty) {
        if (typeWarranty == null) {
            return null;
        }
        return typeWarranty.getValue();
    }

    @Override
    public TypeWarranty convertToEntityAttribute(String value) {
      if (value == null) {
        return null;
      }
      return Stream.of(TypeWarranty.values())
      .filter(c-> c.getValue().equals(value))
      .findFirst()
      .orElseThrow(IllegalArgumentException::new);
    }
    
}
