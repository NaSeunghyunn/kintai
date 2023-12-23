package com.kintai.kintai.service;

import com.kintai.kintai.controller.form.KintaiDetailSaveForm;
import com.kintai.kintai.domain.entity.Kintai;
import com.kintai.kintai.domain.entity.KintaiDetail;
import com.kintai.kintai.repository.KintaiDetailRepository;
import com.kintai.kintai.repository.KintaiRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class KintaiDetailService {
    private final KintaiRepository kintaiRepository;
    private final KintaiDetailRepository kintaiDetailRepository;
    public Long save(KintaiDetailSaveForm saveForm) {
        Kintai kintai = kintaiRepository.getReferenceById(saveForm.getKintaiId());
        KintaiDetail kintaiDetail = KintaiDetail.builder()
                .id(saveForm.getDetailId())
                .kintai(kintai)
                .date(saveForm.getDate())
                .startTime(saveForm.getStartTime())
                .endTime(saveForm.getEndTime())
                .breakTimeHours(saveForm.getBreakTimeHours())
                .workType(saveForm.getWorkType())
                .workDesc(saveForm.getWorkDesc())
                .note(saveForm.getNote())
                .build();
        kintaiDetailRepository.save(kintaiDetail);
        return kintaiDetail.getId();
    }
}
