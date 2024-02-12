package com.kintai.kintai.repository.custom;

import com.kintai.kintai.domain.entity.Kintai;

import java.time.LocalDate;

public interface KintaiDetailRepositoryCustom {
    boolean isMe(Long id, Long memberId);

    Long findId(Kintai kintai, LocalDate date);
}
