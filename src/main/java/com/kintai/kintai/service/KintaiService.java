package com.kintai.kintai.service;

import com.kintai.kintai.controller.form.KintaiSubmitForm;
import com.kintai.kintai.domain.entity.Kintai;
import com.kintai.kintai.domain.entity.Member;
import com.kintai.kintai.domain.utils.KintaiBuilder;
import com.kintai.kintai.dto.KintaiDto;
import com.kintai.kintai.dto.KintaiExcelList;
import com.kintai.kintai.dto.KintaiExcelListCond;
import com.kintai.kintai.repository.KintaiRepository;
import com.kintai.kintai.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.YearMonth;
import java.util.List;

import static com.kintai.kintai.domain.KintaiStatus.IN_PROGRESS;

@RequiredArgsConstructor
@Transactional
@Service
public class KintaiService {

    private final KintaiRepository kintaiRepository;
    private final MemberRepository memberRepository;
    private final KintaiBuilder builder;

    @Transactional(readOnly = true)
    public KintaiDto findKintai(Long kintaiId) {
        KintaiDto kintai = kintaiRepository.findKintaiOfMonth(kintaiId);
        return builder.populateMissingDetails(kintai);
    }

    public Long saveAndGetId(Long memberId, YearMonth yearMonth) {
        Long kintaiIdOfMonth = kintaiRepository.findKintaiIdOfMonth(memberId, yearMonth);
        if (kintaiIdOfMonth == null) {
            Member member = memberRepository.getReferenceById(memberId);
            Kintai kintai = Kintai.builder()
                    .status(IN_PROGRESS)
                    .member(member)
                    .workYearMonth(yearMonth)
                    .build();
            kintaiRepository.save(kintai);
            kintaiIdOfMonth = kintai.getId();
        }
        return kintaiIdOfMonth;
    }

    public Long submit(KintaiSubmitForm form) {
        Kintai kintai = kintaiRepository.findById(form.getId())
                .orElseThrow(() -> new IllegalArgumentException("データが存在しません。"));
        kintai.submit(form.getStatus());
        return kintai.getId();
    }

    public List<KintaiExcelList> findExcelTarget(KintaiExcelListCond condition) {
        return kintaiRepository.findExcelTarget(condition);
    }
}