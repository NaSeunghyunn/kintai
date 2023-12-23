package com.kintai.kintai.controller.api;

import com.kintai.kintai.controller.form.KintaiDetailSaveForm;
import com.kintai.kintai.service.KintaiDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/kintai/detail")
@RestController
public class KintaiDetailController {
    private final KintaiDetailService kintaiDetailService;

    @PostMapping()
    public Long save(@Validated @RequestBody KintaiDetailSaveForm saveForm) {
        return kintaiDetailService.save(saveForm);
    }
}
