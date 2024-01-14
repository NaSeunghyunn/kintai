package com.kintai.kintai.domain.excel;

import com.kintai.kintai.domain.CompanyType;
import com.kintai.kintai.domain.utils.TimeUtils;
import com.kintai.kintai.dto.KintaiDetailDto;
import com.kintai.kintai.dto.KintaiDto;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class MoaKintaiExcelDownloader implements KintaiExcelDownloader {
    private final TimeUtils timeUtils;
    private static final String TEMPLATE_FILE_PATH = "static/excelTemplate/moa_kintai_template.xlsx";
    private static final int HEADER_YEAR_ROW_NUM = 3;
    private static final int HEADER_YEAR_CELL_NUM = 1;
    private static final int HEADER_MONTH_ROW_NUM = 3;
    private static final int HEADER_MONTH_CELL_NUM = 4;
    private static final int HEADER_NAME_ROW_NUM = 3;
    private static final int HEADER_NAME_CELL_NUM = 21;
    private static final int FOOTER_TOTAL_WORK_TIME_CELL_NUM = 15;
    private static final int FOOTER_TOTAL_WORK_TIME_CELL_NUM2 = 26;
    private static final int START_DETAIL_ROW_NUM = 6;
    private static final int START_DETAIL_CELL_NUM = 1;

    @Override
    public byte[] write(KintaiDto kintai) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
             XSSFWorkbook workbook = new XSSFWorkbook(new ClassPathResource(TEMPLATE_FILE_PATH).getInputStream())) {
            XSSFSheet sheet = workbook.getSheetAt(0);

            YearMonth workYearMonth = kintai.getWorkYearMonth();
            sheet.getRow(HEADER_YEAR_ROW_NUM).getCell(HEADER_YEAR_CELL_NUM).setCellValue(workYearMonth.getYear());
            sheet.getRow(HEADER_MONTH_ROW_NUM).getCell(HEADER_MONTH_CELL_NUM).setCellValue(workYearMonth.getMonthValue());
            sheet.getRow(HEADER_NAME_ROW_NUM).getCell(HEADER_NAME_CELL_NUM).setCellValue(kintai.getMemberName());

            XSSFRow rowTemp = sheet.getRow(START_DETAIL_ROW_NUM);
            CellCopyPolicy ccp = new CellCopyPolicy();
            int endOfMonth = workYearMonth.atEndOfMonth().getDayOfMonth();
            sheet.shiftRows(START_DETAIL_ROW_NUM, sheet.getLastRowNum(), endOfMonth - 1, true, false);
            Map<Integer, KintaiDetailDto> details = kintai.getDetails().stream().collect(Collectors.toMap(KintaiDetailDto::getDay, v -> v));

            int rowNum = START_DETAIL_ROW_NUM;
            for (int day = 1; day <= endOfMonth; day++) {
                XSSFRow row = rowTemp;
                if (day != endOfMonth) {
                    row = sheet.createRow(rowNum++);
                    row.copyRowFrom(rowTemp, ccp);
                }

                LocalDate date = workYearMonth.atDay(day);
                KintaiDetailDto detail = details.getOrDefault(day, KintaiDetailDto.kintaiDefault(date));

                int cellNum = START_DETAIL_CELL_NUM;
                row.getCell(cellNum++).setCellValue(workYearMonth.atDay(day));
                row.getCell(cellNum++).setCellValue(detail.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.JAPANESE));
                if (detail.getStartTime() != null) {
                    row.getCell(cellNum).setCellValue(timeUtils.formatTime(detail.getStartTime()));
                }
                cellNum += 4;
                if (detail.getEndTime() != null) {
                    row.getCell(cellNum).setCellValue(timeUtils.formatTime(detail.getEndTime()));
                }
                cellNum += 4;
                if (detail.getBreakTimeHours() > 0) {
                    row.getCell(cellNum).setCellValue(detail.getBreakTimeHours() + ":00");
                }
                cellNum += 8;
                row.getCell(cellNum).setCellValue(detail.getNote());
            }

            SheetConditionalFormatting conditionalFormatting = sheet.getSheetConditionalFormatting();
            ConditionalFormattingRule ruleSunday = conditionalFormatting.createConditionalFormattingRule("WEEKDAY($B7)=1");
            FontFormatting fontFormattingSunday = ruleSunday.createFontFormatting();
            fontFormattingSunday.setFontColorIndex(IndexedColors.RED.index);

            ConditionalFormattingRule ruleSaturday = conditionalFormatting.createConditionalFormattingRule("WEEKDAY($B7)=7");
            FontFormatting fontFormattingSaturday = ruleSaturday.createFontFormatting();
            fontFormattingSaturday.setFontColorIndex(IndexedColors.BLUE.index);

            CellRangeAddress range = new CellRangeAddress(START_DETAIL_ROW_NUM, rowNum, START_DETAIL_CELL_NUM, START_DETAIL_CELL_NUM + 1);
            CellRangeAddress[] cellRanges = {range};
            conditionalFormatting.addConditionalFormatting(cellRanges, ruleSunday, ruleSaturday);

            sheet.getRow(++rowNum).getCell(FOOTER_TOTAL_WORK_TIME_CELL_NUM).setCellFormula("SUM(P" + (START_DETAIL_ROW_NUM + 1) + ":S" + (START_DETAIL_ROW_NUM + endOfMonth) + ")");
            sheet.getRow(rowNum + 2).getCell(FOOTER_TOTAL_WORK_TIME_CELL_NUM2).setCellFormula("P" + (rowNum + 1));

            FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
            evaluator.evaluateAll();
            workbook.write(outputStream);
            return outputStream.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean support(CompanyType type) {
        return CompanyType.MOA == type;
    }
}
