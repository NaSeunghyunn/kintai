package com.kintai.kintai.controller.api;

import com.kintai.kintai.auth.Login;
import com.kintai.kintai.auth.LoginMember;
import com.kintai.kintai.dto.KintaiDto;
import com.kintai.kintai.service.KintaiService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.YearMonth;

@RequiredArgsConstructor
@RequestMapping("/api/kintai")
@RestController
public class KintaiController {
    private final KintaiService kintaiService;

    @GetMapping()
    public KintaiDto find(YearMonth yearMonth, @Login LoginMember member) {
        return kintaiService.findKintai(member.getId(), yearMonth);
    }

}
