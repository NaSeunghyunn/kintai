package com.kintai.kintai.domain.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@RequiredArgsConstructor
@Component
public class HolidayApi {

    private final ApiUtils api;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public List<LocalDate> getHolidays(YearMonth yearMonth) {
        final String url = "https://holidays-jp.github.io/api/v1/" + yearMonth.getYear() + "/date.json";
        HttpResponse<String> response = api.get(url);
        try {
            JsonNode jsonNode = objectMapper.readTree(response.body());
            List<LocalDate> result = new ArrayList<>();
            for (Iterator<String> it = jsonNode.fieldNames(); it.hasNext(); ) {
                String fieldName = it.next();
                LocalDate holiday = LocalDate.parse(fieldName, DateTimeFormatter.ISO_LOCAL_DATE);
                if (YearMonth.from(holiday).equals(yearMonth)) {
                    result.add(holiday);
                }
            }
            return result;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
