package com.kintai.kintai.controller.form;

import com.kintai.kintai.domain.KintaiStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class KintaiSubmitForm {
    @NotNull
    private Long id;
    
    @NotNull
    private KintaiStatus status;
}
