package com.tcscontrol.control_backend.enuns.converters;

import java.util.stream.Stream;

import com.tcscontrol.control_backend.enuns.DocumentoType;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class DocumentoConverter implements AttributeConverter<DocumentoType, String> {
    
    @Override
    public String convertToDatabaseColumn(DocumentoType documento) {
        if(documento == null){
            return null;
        }
        return documento.getValue();
    }

    @Override
    public DocumentoType convertToEntityAttribute(String value) {
        if(value == null){
            return null;
        }

        return Stream.of(DocumentoType.values())
        .filter(c -> c.getValue().equals(value))
        .findFirst()
        .orElseThrow(IllegalArgumentException::new);

    }
}
