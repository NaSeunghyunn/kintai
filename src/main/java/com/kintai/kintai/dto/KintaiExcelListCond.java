package com.kintai.kintai.dto;

import com.kintai.kintai.controller.form.KintaiExcelListForm;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.YearMonth;

@AllArgsConstructor
@Getter
public class KintaiExcelListCond {
    private YearMonth yearMonth;

    public static KintaiExcelListCond of(KintaiExcelListForm form) {
        return new KintaiExcelListCond(form.getYearMonth());
    }
}
