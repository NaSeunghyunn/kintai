package com.kintai.kintai.controller.form;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class KintaiDetailDeleteForm {
    @NotNull
    private Long id;
}
