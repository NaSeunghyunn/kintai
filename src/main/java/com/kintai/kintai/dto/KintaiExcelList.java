package com.kintai.kintai.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.YearMonth;

@AllArgsConstructor
@Getter
public class KintaiExcelList {
    private Long id;
    private YearMonth yearMonth;
    private String memberName;
}
