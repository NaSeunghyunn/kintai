package com.kintai.kintai.service;

import com.kintai.kintai.domain.CompanyType;
import com.kintai.kintai.domain.excel.KintaiExcelDownloader;
import com.kintai.kintai.domain.excel.KintaiExcelDownloaderFactory;
import com.kintai.kintai.dto.FileDto;
import com.kintai.kintai.dto.KintaiDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.format.DateTimeFormatter;

@RequiredArgsConstructor
@Transactional
@Service
public class ExcelService {

    private final KintaiService kintaiService;
    private final KintaiExcelDownloaderFactory excelDownloaderFactory;

    public FileDto write(Long kintaiId, CompanyType type, String memberName) {
        KintaiDto kintai = kintaiService.findKintai(kintaiId);
        KintaiExcelDownloader excelDownloader = excelDownloaderFactory.getExcelDownloader(type);

        String fileName = "勤務表_" + kintai.getWorkYearMonth().format(DateTimeFormatter.ofPattern("M月")) + "_" + memberName + ".xlsx";
        return new FileDto(URLEncoder.encode(fileName, StandardCharsets.UTF_8) ,excelDownloader.write(kintai));
    }
}
