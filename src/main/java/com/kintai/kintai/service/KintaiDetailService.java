package com.kintai.kintai.service;

import com.kintai.kintai.controller.form.KintaiDetailSaveForm;
import com.kintai.kintai.domain.entity.Kintai;
import com.kintai.kintai.domain.entity.KintaiDetail;
import com.kintai.kintai.dto.KintaiTodayDto;
import com.kintai.kintai.repository.KintaiDetailRepository;
import com.kintai.kintai.repository.KintaiRepository;
import com.kintai.kintai.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.YearMonth;

import static com.kintai.kintai.domain.KintaiStatus.IN_PROGRESS;

@RequiredArgsConstructor
@Transactional
@Service
public class KintaiDetailService {
    private final KintaiRepository kintaiRepository;
    private final KintaiDetailRepository kintaiDetailRepository;
    private final MemberRepository memberRepository;

    public Long save(KintaiDetailSaveForm saveForm) {
        Kintai kintai = kintaiRepository.getReferenceById(saveForm.getKintaiId());
        Long detailId = saveForm.getDetailId();
        if (detailId == null) {
            detailId = kintaiDetailRepository.findId(kintai, saveForm.getDate());
        }
        KintaiDetail kintaiDetail = KintaiDetail.builder()
                .id(detailId)
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

    public KintaiTodayDto findToday(Long memberId) {
        KintaiTodayDto kintaiToday = kintaiRepository.findToday(memberId);
        if (kintaiToday == null) {
            Long kintaiId = saveKintai(memberId);
            return KintaiTodayDto.of(kintaiId);
        }
        return kintaiToday;
    }

    public Long delete(Long id, Long memberId) {
        validateIsMe(id, memberId);
        kintaiDetailRepository.deleteById(id);
        return id;
    }

    private void validateIsMe(Long id, Long memberId) {
        boolean isMe = kintaiDetailRepository.isMe(id, memberId);
        if (!isMe) throw new IllegalArgumentException("処理がうまくできませんでした。最初からやり直してください。");
    }

    private Long saveKintai(Long memberId) {
        Kintai kintai = Kintai.builder()
                .status(IN_PROGRESS)
                .member(memberRepository.getReferenceById(memberId))
                .workYearMonth(YearMonth.now())
                .build();
        kintaiRepository.save(kintai);
        return kintai.getId();
    }
}
