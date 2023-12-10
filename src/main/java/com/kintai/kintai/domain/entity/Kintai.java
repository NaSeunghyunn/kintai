package com.kintai.kintai.domain.entity;

import com.kintai.kintai.util.YearMonthConverter;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.YearMonth;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Entity
public class Kintai extends BaseEntity {

    @GeneratedValue(strategy = IDENTITY)
    @Id
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Convert(converter = YearMonthConverter.class)
    @Column(nullable = false)
    private YearMonth workYearMonth;

    @Builder
    public Kintai(Long id, Member member, YearMonth workYearMonth) {
        this.id = id;
        this.member = member;
        this.workYearMonth = workYearMonth;
    }
}
