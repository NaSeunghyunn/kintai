package com.kintai.kintai.domain.excel;

import com.kintai.kintai.domain.CompanyType;
import com.kintai.kintai.dto.KintaiDto;

public interface KintaiExcelDownloader {
    byte[] write(KintaiDto dto);

    boolean support(CompanyType type);
}
