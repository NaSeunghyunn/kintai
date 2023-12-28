package com.kintai.kintai.controller.api;

import com.kintai.kintai.controller.form.KintaiSubmitForm;
import com.kintai.kintai.dto.KintaiDto;
import com.kintai.kintai.service.KintaiService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/kintai")
@RestController
public class KintaiController {
    private final KintaiService kintaiService;

    @GetMapping()
    public KintaiDto find(@RequestParam("kintaiId") Long kintaiId) {
        return kintaiService.findKintai(kintaiId);
    }

    @PostMapping("/submit")
    public Long submit(@Validated @RequestBody KintaiSubmitForm form) {
        return kintaiService.submit(form);
    }
}
