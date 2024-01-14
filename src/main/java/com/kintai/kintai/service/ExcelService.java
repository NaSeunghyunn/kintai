package com.kintai.kintai.service;

import com.kintai.kintai.domain.excel.KintaiExcelDownloader;
import com.kintai.kintai.domain.excel.KintaiExcelDownloaderFactory;
import com.kintai.kintai.domain.CompanyType;
import com.kintai.kintai.dto.KintaiDto;
import com.kintai.kintai.repository.KintaiRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class ExcelService {

    private final KintaiRepository kintaiRepository;
    private final KintaiExcelDownloaderFactory excelDownloaderFactory;

    public byte[] write(Long kintaiId, CompanyType type) {
        KintaiDto kintai = kintaiRepository.findKintaiOfMonth(kintaiId);
        KintaiExcelDownloader excelDownloader = excelDownloaderFactory.getExcelDownloader(type);
        return excelDownloader.write(kintai);
    }
}
