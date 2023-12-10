package com.kintai.kintai.dto;

import com.kintai.kintai.domain.WorkType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class KintaiDetailDto {
    private Long id;
    private DayOfWeek dayOfWeek;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int breakTimeHours;
    private WorkType workType;
    private String workDesc;
    private String note;
}
