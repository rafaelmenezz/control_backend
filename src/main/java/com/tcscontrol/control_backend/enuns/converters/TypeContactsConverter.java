package com.tcscontrol.control_backend.enuns.converters;

import java.util.stream.Stream;

import com.tcscontrol.control_backend.enuns.TypeContacts;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class TypeContactsConverter implements AttributeConverter<TypeContacts, String>{

     @Override
    public String convertToDatabaseColumn(TypeContacts typeContacts) {
        if(typeContacts == null){
            return null;
        }
        return typeContacts.getValue();
    }

    @Override
    public TypeContacts convertToEntityAttribute(String value) {
        if(value == null){
            return null;
        }

        return Stream.of(TypeContacts.values())
        .filter(c -> c.getValue().equals(value))
        .findFirst()
        .orElseThrow(IllegalArgumentException::new);

    }
}
