package com.kintai.kintai.service;

import com.kintai.kintai.dto.KintaiDto;
import com.kintai.kintai.repository.KintaiRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.YearMonth;

@RequiredArgsConstructor
@Transactional
@Service
public class KintaiService {

    private final KintaiRepository kintaiRepository;

    @Transactional(readOnly = true)
    public KintaiDto findKintai(Long memberId, YearMonth yearMonth) {
        return kintaiRepository.findKintaiOfMonth(memberId, yearMonth);
    }
}
