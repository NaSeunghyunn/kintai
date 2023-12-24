package com.kintai.kintai.controller.api;

import com.kintai.kintai.service.ExcelService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;

@RequiredArgsConstructor
@RequestMapping("/api/excel")
@RestController
public class ExcelController {

    private final ExcelService excelService;

    @GetMapping("/download")
    public ResponseEntity<FileSystemResource> downloadFile(@RequestParam("id") Long id) {
        File file = excelService.write(id);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", file.getName());

        return ResponseEntity.ok()
                .headers(headers)
                .body(new FileSystemResource(file));

    }
}
