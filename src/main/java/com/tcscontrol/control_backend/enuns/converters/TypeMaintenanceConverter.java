package com.tcscontrol.control_backend.enuns.converters;

import java.util.stream.Stream;
import com.tcscontrol.control_backend.enuns.TypeMaintenance;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class TypeMaintenanceConverter implements AttributeConverter<TypeMaintenance, String>{

      @Override
      public String convertToDatabaseColumn(TypeMaintenance typeMaintenance) {
            if (typeMaintenance == null) {
                  return null;
            }
            return typeMaintenance.getValue();
      }
      @Override
      public TypeMaintenance convertToEntityAttribute(String value) {
         if (value == null) {
            return null;
         }
         return Stream.of(TypeMaintenance.values())
         .filter(m -> m.getValue().equals(value))
         .findFirst()
         .orElseThrow(IllegalArgumentException::new);
      }
      
}
