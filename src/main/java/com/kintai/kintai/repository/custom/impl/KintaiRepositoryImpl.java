package com.kintai.kintai.repository.custom.impl;

import com.kintai.kintai.domain.entity.Kintai;
import com.kintai.kintai.domain.entity.Member;
import com.kintai.kintai.dto.KintaiDetailDto;
import com.kintai.kintai.dto.KintaiDto;
import com.kintai.kintai.repository.custom.KintaiRepositoryCustom;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.YearMonth;

import static com.kintai.kintai.domain.entity.QKintai.kintai;
import static com.kintai.kintai.domain.entity.QKintaiDetail.kintaiDetail;
import static com.kintai.kintai.domain.entity.QMember.member;
import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.list;

@RequiredArgsConstructor
@Repository
public class KintaiRepositoryImpl implements KintaiRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    public KintaiDto findKintaiOfMonth(Long kintaiId) {
        return queryFactory.selectFrom(kintai)
                .innerJoin(kintai.member, member)
                .innerJoin(kintaiDetail)
                .on(kintaiDetail.kintai.eq(kintai))
                .where(
                        kintai.id.eq(kintaiId)
                )
                .orderBy(kintaiDetail.startTime.asc())
                .transform(groupBy(kintai.id).as(Projections.constructor(
                        KintaiDto.class,
                        kintai.id,
                        member.name,
                        kintai.workYearMonth,
                        list(Projections.constructor(
                                        KintaiDetailDto.class,
                                        kintaiDetail.id,
                                        kintaiDetail.date,
                                        kintaiDetail.startTime,
                                        kintaiDetail.endTime,
                                        kintaiDetail.breakTimeHours,
                                        kintaiDetail.workType,
                                        kintaiDetail.workDesc,
                                        kintaiDetail.note
                                )
                        )
                ))).get(kintaiId);
    }

    @Override
    public Long findKintaiIdOfMonth(Long memberId, YearMonth yearMonth) {
        return queryFactory
                .select(kintai.id)
                .from(kintai)
                .where(
                        kintai.member.id.eq(memberId)
                        , kintai.workYearMonth.eq(yearMonth)
                ).fetchFirst();
    }
}
