package com.kintai.kintai.util;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Convert;

import java.time.YearMonth;

@Convert
public class YearMonthConverter implements AttributeConverter<YearMonth, String> {
    @Override
    public String convertToDatabaseColumn(YearMonth attribute) {
        return attribute.toString();
    }

    @Override
    public YearMonth convertToEntityAttribute(String dbData) {
        return YearMonth.parse(dbData);
    }
}
