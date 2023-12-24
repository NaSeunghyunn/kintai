package com.kintai.kintai.dto;

import com.kintai.kintai.domain.WorkType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
public class KintaiDetailDto {
    private Long id;
    private LocalDate date;
    private DayOfWeek dayOfWeek;
    private int day;
    private LocalTime startTime;
    private LocalTime endTime;
    private int breakTimeHours;
    private WorkType workType;
    private String workDesc;
    private String note;

    public KintaiDetailDto(Long id, LocalDate date, LocalTime startTime, LocalTime endTime, int breakTimeHours, WorkType workType, String workDesc, String note) {
        this.id = id;
        this.date = date;
        this.dayOfWeek = date.getDayOfWeek();
        this.day = date.getDayOfMonth();
        this.startTime = startTime;
        this.endTime = endTime;
        this.breakTimeHours = breakTimeHours;
        this.workType = workType;
        this.workDesc = workDesc;
        this.note = note;
    }

    public static KintaiDetailDto kintaiDefault(LocalDate date) {
        boolean isHoliday = date.getDayOfWeek() == DayOfWeek.SUNDAY || date.getDayOfWeek() == DayOfWeek.SATURDAY;
        if(isHoliday) return holidayDefault(date);
        return workingDayDefault(date);
    }

    public static KintaiDetailDto workingDayDefault(LocalDate date) {
        return new KintaiDetailDto(null, date, LocalTime.of(9, 0, 0), LocalTime.of(18, 0, 0), 1, WorkType.WORK, "", "");
    }

    public static KintaiDetailDto holidayDefault(LocalDate date) {
        return new KintaiDetailDto(null, date, null, null, 0, WorkType.VACATION, "", "");

    }
}
