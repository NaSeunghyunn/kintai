package com.kintai.kintai.controller;

import com.kintai.kintai.auth.Login;
import com.kintai.kintai.auth.LoginMember;
import com.kintai.kintai.controller.form.KintaiExcelListForm;
import com.kintai.kintai.dto.KintaiExcelList;
import com.kintai.kintai.dto.KintaiExcelListCond;
import com.kintai.kintai.service.KintaiService;
import com.kintai.kintai.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.YearMonth;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/kintai")
@Controller
public class KintaiWebController {

    private final KintaiService kintaiService;
    private final MemberService memberService;

    @GetMapping()
    public String init(@RequestParam(name = "yearMonth", required = false) YearMonth yearMonth, @Login LoginMember member, Model model) {
        if (yearMonth == null) yearMonth = YearMonth.now();
        Long id = kintaiService.saveAndGetId(member.getId(), yearMonth);
        model.addAttribute("id", id);
        model.addAttribute("yearMonth", yearMonth);
        model.addAttribute("memberName", member.getName());
        return "kintai/kintai";
    }

    @GetMapping("/excel")
    public String excel(@ModelAttribute("form") KintaiExcelListForm form, @Login LoginMember member, Model model) {
        List<String> companyNames = memberService.findCompanyNames(member.getId());
        model.addAttribute("companies", companyNames);
        return "kintai/excel";
    }

    @GetMapping("/excel/download/{company}")
    public String excelDownload(@Validated @ModelAttribute("form") KintaiExcelListForm form, @PathVariable("company") String company, Model model) {
        KintaiExcelListCond condition = KintaiExcelListCond.of(form);
        List<KintaiExcelList> excelLists = kintaiService.findExcelTarget(condition);
        List<YearMonth> yearMonths = Arrays.asList(YearMonth.now(), YearMonth.now().minusMonths(1), YearMonth.now().minusMonths(2));
        model.addAttribute("excelLists", excelLists);
        model.addAttribute("yearMonths", yearMonths);
        model.addAttribute("company", company);
        return "kintai/excelDownload";
    }
}
