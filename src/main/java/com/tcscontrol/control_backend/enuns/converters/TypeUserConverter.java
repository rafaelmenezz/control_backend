package com.tcscontrol.control_backend.enuns.converters;

import java.util.stream.Stream;

import com.tcscontrol.control_backend.enuns.TypeUser;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class TypeUserConverter implements AttributeConverter<TypeUser, String> {

    @Override
    public String convertToDatabaseColumn(TypeUser typeUser) {
        if(typeUser == null){
            return null;
        }
        return typeUser.getValue();
    }

    @Override
    public TypeUser convertToEntityAttribute(String value) {
        if(value == null){
            return null;
        }
        return Stream.of(TypeUser.values())
        .filter(c -> c.getValue().equals(value))
        .findFirst()
        .orElseThrow(IllegalArgumentException::new);

    }
    
}