package dev.cristovantamayo.ecommerce.model.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class BooleanYesNoConverter implements AttributeConverter<Boolean, String> {
    @Override
    public String convertToDatabaseColumn(Boolean aBoolean) {
        return Boolean.TRUE.equals(aBoolean) ? "YES" : "NO";
    }

    @Override
    public Boolean convertToEntityAttribute(String s) {
        return "YES".equals(s) ? Boolean.TRUE : Boolean.FALSE;
    }
}
