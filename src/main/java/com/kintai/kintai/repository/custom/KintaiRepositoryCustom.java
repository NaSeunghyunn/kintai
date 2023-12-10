package com.kintai.kintai.repository.custom;

import com.kintai.kintai.dto.KintaiDto;

import java.time.YearMonth;

public interface KintaiRepositoryCustom {
    KintaiDto findKintaiOfMonth(Long memberId, YearMonth yearMonth);
}
