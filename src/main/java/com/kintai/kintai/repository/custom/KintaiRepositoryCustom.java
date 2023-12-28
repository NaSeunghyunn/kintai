package com.kintai.kintai.repository.custom;

import com.kintai.kintai.dto.KintaiDto;
import com.kintai.kintai.dto.KintaiTodayDto;

import java.time.YearMonth;

public interface KintaiRepositoryCustom {
    KintaiDto findKintaiOfMonth(Long kintaiId);

    Long findKintaiIdOfMonth(Long memberId, YearMonth yearMonth);

    KintaiTodayDto findToday(Long memberId);
}
