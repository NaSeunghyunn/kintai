package com.kintai.kintai.repository.custom.impl;

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

    public KintaiDto findKintaiOfMonth(Long memberId, YearMonth yearMonth) {
        return queryFactory.selectFrom(kintai)
                .innerJoin(kintai.member, member)
                .leftJoin(kintaiDetail)
                .on(kintaiDetail.kintai.eq(kintai))
                .where(
                        kintai.member.id.eq(memberId),
                        kintai.workYearMonth.eq(yearMonth)
                )
                .transform(groupBy(member.id).as(Projections.constructor(
                        KintaiDto.class,
                        kintai.id,
                        member.name,
                        kintai.workYearMonth,
                        list(Projections.constructor(
                                        KintaiDetailDto.class,
                                        kintaiDetail.id,
                                        kintaiDetail.dayOfWeek,
                                        kintaiDetail.startTime,
                                        kintaiDetail.endTime,
                                        kintaiDetail.breakTimeHours,
                                        kintaiDetail.workType,
                                        kintaiDetail.workDesc,
                                        kintaiDetail.note
                                )
                        )
                ))).get(memberId);
    }
}
