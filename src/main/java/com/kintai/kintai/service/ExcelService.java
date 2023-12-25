package com.kintai.kintai.service;

import com.kintai.kintai.domain.excel.SmtExcelDownloader;
import com.kintai.kintai.domain.utils.TimeUtils;
import com.kintai.kintai.dto.KintaiDetailDto;
import com.kintai.kintai.dto.KintaiDto;
import com.kintai.kintai.repository.KintaiRepository;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.CellCopyPolicy;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
@Service
public class ExcelService {

    private final KintaiRepository kintaiRepository;
    private final SmtExcelDownloader excelDownloader;

    public byte[] write(Long kintaiId) {
        KintaiDto kintai = kintaiRepository.findKintaiOfMonth(kintaiId);
        return excelDownloader.write(kintai);
    }
}
