package com.kintai.kintai.controller.form;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.YearMonth;

@Data
public class KintaiSaveForm {
    @NotNull
    private YearMonth yearMonth;
}
