package com.kintai.kintai.domain.utils;

import com.kintai.kintai.domain.api.HolidayApi;
import com.kintai.kintai.dto.KintaiDetailDto;
import com.kintai.kintai.dto.KintaiDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class KintaiBuilder {

    private final HolidayApi holidayApi;

    public KintaiDto populateMissingDetails(KintaiDto dbData) {
        List<KintaiDetailDto> details = new ArrayList<>(dbData.getDetails());
        Set<Integer> existingDays = details.stream().map(KintaiDetailDto::getDay).collect(Collectors.toSet());
        WorkDetailsGenerator workDetailsGenerator = new WorkDetailsGenerator(holidayApi.getHolidays(dbData.getWorkYearMonth()));
        int lastDayOfMonth = dbData.getWorkYearMonth().lengthOfMonth();
        for (int day = 1; day <= lastDayOfMonth; day++) {
            if (existingDays.contains(day)) {
                continue;
            }
            LocalDate date = dbData.getWorkYearMonth().atDay(day);
            KintaiDetailDto kintaiDetail = workDetailsGenerator.generateWorkDetails(date);
            details.add(kintaiDetail);
        }
        details.sort(Comparator.comparingInt(KintaiDetailDto::getDay));
        return new KintaiDto(dbData.getId(), dbData.getStatus(), dbData.getMemberName(), dbData.getWorkYearMonth(), details);
    }
}
