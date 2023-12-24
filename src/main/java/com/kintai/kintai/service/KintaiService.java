package com.kintai.kintai.service;

import com.kintai.kintai.controller.form.KintaiSaveForm;
import com.kintai.kintai.domain.entity.Kintai;
import com.kintai.kintai.domain.entity.Member;
import com.kintai.kintai.dto.KintaiDto;
import com.kintai.kintai.repository.KintaiRepository;
import com.kintai.kintai.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.YearMonth;

@RequiredArgsConstructor
@Transactional
@Service
public class KintaiService {

    private final KintaiRepository kintaiRepository;
    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public KintaiDto findKintai(Long kintaiId) {
        return kintaiRepository.findKintaiOfMonth(kintaiId);
    }

    public Long saveAndGetId(Long memberId, YearMonth yearMonth) {
        Long kintaiIdOfMonth = kintaiRepository.findKintaiIdOfMonth(memberId, yearMonth);
        if (kintaiIdOfMonth == null) {
            Member member = memberRepository.getReferenceById(memberId);
            Kintai kintai = Kintai.builder()
                    .member(member)
                    .workYearMonth(yearMonth)
                    .build();
            kintaiRepository.save(kintai);
            kintaiIdOfMonth = kintai.getId();
        }
        return kintaiIdOfMonth;
    }

    public String download(Long memberId, YearMonth yearMonth) {

        return null;
    }
}

