package com.kintai.kintai.domain.excel;

import com.kintai.kintai.domain.CompanyType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class KintaiExcelDownloaderFactory {
    private final List<KintaiExcelDownloader> downloaders;

    public KintaiExcelDownloader getExcelDownloader(CompanyType type) {
        return downloaders.stream().filter(downloader -> downloader.support(type)).findAny().orElse(null);
    }
}
