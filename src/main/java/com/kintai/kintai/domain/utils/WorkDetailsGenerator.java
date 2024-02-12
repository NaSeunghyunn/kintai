package com.kintai.kintai.domain.utils;

import com.kintai.kintai.domain.WorkType;
import com.kintai.kintai.dto.KintaiDetailDto;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class WorkDetailsGenerator {
    private final List<LocalDate> holidays;

    public WorkDetailsGenerator(List<LocalDate> holidays) {
        this.holidays = holidays;
    }

    public KintaiDetailDto generateWorkDetails(final LocalDate date) {
        if (isWeekend(date)) {
            return createWeekendDetails(date);
        }
        if (isHoliday(date)) {
            return createHolidayDetails(date);
        }
        return createWorkingDayDetails(date);
    }

    private boolean isWeekend(LocalDate date) {
        return date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY;
    }

    private boolean isHoliday(LocalDate date) {
        return holidays.contains(date);
    }

    private KintaiDetailDto createWorkingDayDetails(LocalDate date) {
        return KintaiDetailDto.builder()
                .date(date)
                .startTime(LocalTime.of(9, 0))
                .endTime(LocalTime.of(18, 0))
                .breakTimeHours(1)
                .workType(WorkType.WORK)
                .build();
    }

    private KintaiDetailDto createWeekendDetails(LocalDate date) {
        return KintaiDetailDto.builder()
                .date(date)
                .workType(WorkType.VACATION)
                .build();
    }

    private KintaiDetailDto createHolidayDetails(LocalDate date) {
        return KintaiDetailDto.builder()
                .date(date)
                .workType(WorkType.VACATION)
                .note("祝日")
                .build();
    }
}
