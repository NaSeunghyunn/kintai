package com.kintai.kintai.controller;

import com.kintai.kintai.auth.Login;
import com.kintai.kintai.auth.LoginMember;
import com.kintai.kintai.service.KintaiService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.YearMonth;

@RequiredArgsConstructor
@RequestMapping("/kintai")
@Controller
public class KintaiWebController {

    private final KintaiService kintaiService;

    @GetMapping()
    public String init(@RequestParam("yearMonth") YearMonth yearMonth, @Login LoginMember member, Model model) {
        Long id = kintaiService.saveAndGetId(member.getId(), yearMonth);
        model.addAttribute("id", id);
        model.addAttribute("yearMonth", yearMonth);
        model.addAttribute("memberName", member.getName());
        return "kintai/kintai";
    }
}
