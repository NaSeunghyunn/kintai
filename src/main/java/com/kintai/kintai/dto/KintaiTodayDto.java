package com.kintai.kintai.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

@AllArgsConstructor
@Getter
public class KintaiTodayDto {
    private Long kintaiId;
    private Long detailId;
    private LocalDate date;
    private LocalTime start;
    private LocalTime end;

    public static KintaiTodayDto of(Long kintaiId) {
        return new KintaiTodayDto(kintaiId, null, null, null, null);
    }
}
