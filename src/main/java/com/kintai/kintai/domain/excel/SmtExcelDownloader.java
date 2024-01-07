package com.kintai.kintai.domain.excel;

import com.kintai.kintai.domain.utils.TimeUtils;
import com.kintai.kintai.dto.KintaiDetailDto;
import com.kintai.kintai.dto.KintaiDto;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.CellCopyPolicy;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

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
@Component
public class SmtExcelDownloader {
    private final TimeUtils timeUtils;
    private static final String TEMPLATE_FILE_PATH = "static/excelTemplate/SMT_kintai_template.xlsx";
    private static final int NAME_ROW_NUM = 3;
    private static final int NAME_CELL_NUM = 1;
    private static final int YEAR_MONTH_ROW_NUM = 4;
    private static final int YEAR_MONTH_CELL_NUM = 0;
    private static final int START_DETAIL_ROW_NUM = 6;
    private static final int TOTAL_WORK_TIME_CELL_NUM = 6;

    public byte[] write(KintaiDto kintai) {
        YearMonth workYearMonth = kintai.getWorkYearMonth();
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
             XSSFWorkbook workbook = new XSSFWorkbook(new ClassPathResource(TEMPLATE_FILE_PATH).getInputStream())) {
            XSSFSheet sheet = workbook.getSheetAt(0);
            sheet.getRow(NAME_ROW_NUM).getCell(NAME_CELL_NUM).setCellValue(kintai.getMemberName());
            String yearMonth = workYearMonth.format(DateTimeFormatter.ofPattern("yyyy年 MM月度"));
            sheet.getRow(YEAR_MONTH_ROW_NUM).getCell(YEAR_MONTH_CELL_NUM).setCellValue(yearMonth);

            int dayOfMonth = workYearMonth.atEndOfMonth().getDayOfMonth();
            XSSFRow rowTemp = sheet.getRow(START_DETAIL_ROW_NUM);
            CellCopyPolicy ccp = new CellCopyPolicy();
            sheet.shiftRows(START_DETAIL_ROW_NUM, sheet.getLastRowNum(), dayOfMonth - 1, true, false);
            Map<Integer, KintaiDetailDto> details = kintai.getDetails().stream().collect(Collectors.toMap(KintaiDetailDto::getDay, v -> v));

            double totalWorkTime = 0;
            int rowNum = START_DETAIL_ROW_NUM;
            for (int day = 1; day <= dayOfMonth; day++) {
                LocalDate date = workYearMonth.atDay(day);
                KintaiDetailDto detail = details.getOrDefault(day, KintaiDetailDto.kintaiDefault(date));
                XSSFRow row = day == dayOfMonth ? rowTemp : sheet.createRow(rowNum++);
                if (day != dayOfMonth) {
                    row.copyRowFrom(rowTemp, ccp);
                }
                int cellNum = 0;
                row.getCell(cellNum++).setCellValue(detail.getDay());
                row.getCell(cellNum++).setCellValue(detail.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.JAPANESE));
                row.getCell(cellNum++).setCellValue(timeUtils.formatTime(detail.getStartTime()));
                cellNum++;
                row.getCell(cellNum++).setCellValue(timeUtils.formatTime(detail.getEndTime()));
                cellNum++;
                LocalTime workTime = timeUtils.durationTime(detail.getStartTime(), detail.getEndTime());
                if (workTime != null) workTime = workTime.minusHours(detail.getBreakTimeHours());
                row.getCell(cellNum++).setCellValue(timeUtils.formatTime(workTime));
                totalWorkTime += timeUtils.toDecimalTime(workTime);
                String breakTime = detail.getBreakTimeHours() == 0 ? "" : detail.getBreakTimeHours() + ":00";
                row.getCell(cellNum++).setCellValue(breakTime);
                row.getCell(cellNum++).setCellValue(detail.getWorkType().getJa());
                cellNum += 3;
                row.getCell(cellNum++).setCellValue(detail.getWorkDesc());
                row.getCell(cellNum).setCellValue(detail.getNote());
            }
            sheet.getRow(++rowNum).getCell(TOTAL_WORK_TIME_CELL_NUM).setCellValue(timeUtils.toFormatTime(totalWorkTime));
            workbook.write(outputStream);
            return outputStream.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
