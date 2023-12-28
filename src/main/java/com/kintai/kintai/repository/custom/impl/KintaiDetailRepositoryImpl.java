package com.kintai.kintai.repository.custom.impl;

import com.kintai.kintai.repository.custom.KintaiDetailRepositoryCustom;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

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
                .join(kintaiDetail.kintai)
                .where(
                        kintaiDetail.id.eq(id),
                        kintai.member.id.eq(memberId)
                )
                .fetchFirst() != null;
    }
}
