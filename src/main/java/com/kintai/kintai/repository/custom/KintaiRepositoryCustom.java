package com.kintai.kintai.repository.custom;

import com.kintai.kintai.domain.entity.Member;
import com.kintai.kintai.dto.KintaiDto;

import java.time.YearMonth;

public interface KintaiRepositoryCustom {
    KintaiDto findKintaiOfMonth(Long kintaiId);

    Long findKintaiIdOfMonth(Long memberId, YearMonth yearMonth);
}
