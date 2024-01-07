package com.kintai.kintai.controller.form;

import lombok.Data;

import java.time.YearMonth;

@Data
public class KintaiExcelListForm {
    private YearMonth yearMonth = YearMonth.now();
    private String memberName;
}
