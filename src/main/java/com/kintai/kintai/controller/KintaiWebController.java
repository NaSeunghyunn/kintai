package com.kintai.kintai.controller;

import com.kintai.kintai.auth.Login;
import com.kintai.kintai.auth.LoginMember;
import com.kintai.kintai.controller.form.KintaiExcelListForm;
import com.kintai.kintai.dto.KintaiExcelList;
import com.kintai.kintai.dto.KintaiExcelListCond;
import com.kintai.kintai.service.KintaiService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.YearMonth;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/kintai")
@Controller
public class KintaiWebController {

    private final KintaiService kintaiService;

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
    public String excel(@ModelAttribute("form") KintaiExcelListForm form, Model model) {
        KintaiExcelListCond condition = KintaiExcelListCond.of(form);
        List<KintaiExcelList> excelLists = kintaiService.findExcelTarget(condition);
        List<YearMonth> yearMonths = Arrays.asList(YearMonth.now(), YearMonth.now().minusMonths(1), YearMonth.now().minusMonths(2));
        model.addAttribute("excelLists", excelLists);
        model.addAttribute("yearMonths", yearMonths);
        return "kintai/excel";
    }
}
