package com.kintai.kintai.controller.form;

import com.kintai.kintai.domain.WorkType;
import lombok.Data;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

@Data
public class KintaiDetailSaveForm {
    private Long id;
    private DayOfWeek dayOfWeek;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int breakTimeHours;
    private WorkType workType;
    private String workDesc;
    private String note;
}
