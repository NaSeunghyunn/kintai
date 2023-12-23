package com.kintai.kintai.controller.form;

import com.kintai.kintai.domain.WorkType;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
public class KintaiDetailSaveForm {
    @NotNull
    private Long kintaiId;
    private Long detailId;
    @NotNull
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private int breakTimeHours;
    @NotNull
    private WorkType workType;
    private String workDesc;
    private String note;
}
