package com.kintai.kintai.controller.api;

import com.kintai.kintai.auth.Login;
import com.kintai.kintai.auth.LoginMember;
import com.kintai.kintai.domain.CompanyType;
import com.kintai.kintai.dto.FileDto;
import com.kintai.kintai.service.ExcelService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/excel")
@RestController
public class ExcelController {

    private final ExcelService excelService;

    @GetMapping("/kintai")
    public ResponseEntity<Resource> downloadFile(@RequestParam("id") Long id, @RequestParam("type") CompanyType type, @Login LoginMember member) {
        FileDto excelData = excelService.write(id, type, member.getName());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

        headers.setContentDispositionFormData("attachment", excelData.getFileName());

        return ResponseEntity.ok()
                .headers(headers)
                .body(new ByteArrayResource(excelData.getFile()));
    }
}
