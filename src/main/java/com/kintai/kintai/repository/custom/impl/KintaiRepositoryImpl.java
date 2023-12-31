package com.kintai.kintai.repository.custom.impl;

import com.kintai.kintai.domain.KintaiStatus;
import com.kintai.kintai.dto.*;
import com.kintai.kintai.repository.custom.KintaiRepositoryCustom;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

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
                .leftJoin(kintaiDetail)
                .on(kintaiDetail.kintai.eq(kintai))
                .where(
                        kintai.id.eq(kintaiId)
                )
                .orderBy(kintaiDetail.startTime.asc())
                .transform(groupBy(kintai.id).as(Projections.constructor(
                        KintaiDto.class,
                        kintai.id,
                        kintai.status,
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

    @Override
    public KintaiTodayDto findToday(Long memberId) {
        return queryFactory.select(Projections.constructor(
                        KintaiTodayDto.class,
                        kintai.id,
                        kintaiDetail.id,
                        kintaiDetail.date,
                        kintaiDetail.startTime,
                        kintaiDetail.endTime,
                        kintaiDetail.breakTimeHours
                ))
                .from(kintai)
                .leftJoin(kintaiDetail)
                .on(
                        kintaiDetail.kintai.eq(kintai),
                        kintaiDetail.date.eq(LocalDate.now())
                )
                .where(
                        kintai.member.id.eq(memberId),
                        kintai.workYearMonth.eq(YearMonth.now())
                )
                .fetchFirst();
    }

    @Override
    public List<KintaiExcelList> findExcelTarget(KintaiExcelListCond condition) {
        return queryFactory
                .select(Projections.constructor(KintaiExcelList.class,
                        kintai.id,
                        kintai.workYearMonth,
                        kintai.member.name
                ))
                .from(kintai)
                .join(kintai.member, member)
                .where(
                        yearMonthEq(condition.getYearMonth()),
                        kintai.status.eq(KintaiStatus.COMPLETED)
                )
                .fetch();
    }

    private BooleanExpression yearMonthEq(YearMonth yearMonth) {
        return yearMonth == null ? null : kintai.workYearMonth.eq(yearMonth);
    }

}
