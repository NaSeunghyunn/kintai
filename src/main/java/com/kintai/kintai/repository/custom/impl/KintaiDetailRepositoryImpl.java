package com.kintai.kintai.repository.custom.impl;

import com.kintai.kintai.domain.entity.Kintai;
import com.kintai.kintai.repository.custom.KintaiDetailRepositoryCustom;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

import static com.kintai.kintai.domain.entity.QKintai.kintai;
import static com.kintai.kintai.domain.entity.QKintaiDetail.kintaiDetail;

@RequiredArgsConstructor
@Repository
public class KintaiDetailRepositoryImpl implements KintaiDetailRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public boolean isMe(Long id, Long memberId) {
        return queryFactory
                .selectOne()
                .from(kintaiDetail)
                .join(kintaiDetail.kintai, kintai)
                .where(
                        kintaiDetail.id.eq(id),
                        kintai.member.id.eq(memberId)
                )
                .fetchFirst() != null;
    }

    @Override
    public Long findId(Kintai kintai, LocalDate date) {
        return queryFactory
                .select(kintaiDetail.id)
                .from(kintaiDetail)
                .where(
                        kintaiDetail.kintai.eq(kintai),
                        kintaiDetail.date.eq(date)
                ).fetchFirst();
    }
}
