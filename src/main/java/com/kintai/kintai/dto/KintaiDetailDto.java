package com.kintai.kintai.dto;

import com.kintai.kintai.domain.WorkType;
import lombok.Builder;
import lombok.Getter;

import java.time.DayOfWeek;
import java.time.LocalDate;
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

    @Builder
    public KintaiDetailDto(Long id, LocalDate date, LocalTime startTime, LocalTime endTime, int breakTimeHours, WorkType workType, String workDesc, String note) {
        this.id = id;
        this.date = date;
        this.dayOfWeek = date == null ? null : date.getDayOfWeek();
        this.day = date == null ? 0 : date.getDayOfMonth();
        this.startTime = startTime;
        this.endTime = endTime;
        this.breakTimeHours = breakTimeHours;
        this.workType = workType;
        this.workDesc = workDesc;
        this.note = note;
    }
}
