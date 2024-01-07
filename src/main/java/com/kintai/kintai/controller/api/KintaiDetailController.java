package com.kintai.kintai.controller.api;

import com.kintai.kintai.auth.Login;
import com.kintai.kintai.auth.LoginMember;
import com.kintai.kintai.controller.form.KintaiDetailDeleteForm;
import com.kintai.kintai.controller.form.KintaiDetailSaveForm;
import com.kintai.kintai.dto.KintaiTodayDto;
import com.kintai.kintai.service.KintaiDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/kintai/detail")
@RestController
public class KintaiDetailController {
    private final KintaiDetailService kintaiDetailService;

    @PostMapping()
    public Long save(@Validated @RequestBody KintaiDetailSaveForm saveForm) {
        return kintaiDetailService.save(saveForm);
    }

    @GetMapping()
    public KintaiTodayDto findToday(@Login LoginMember member) {
        return kintaiDetailService.findToday(member.getId());
    }

    @DeleteMapping()
    public Long delete(@Validated @RequestBody KintaiDetailDeleteForm form, @Login LoginMember member) {
        return kintaiDetailService.delete(form.getId(), member.getId());
    }
}
