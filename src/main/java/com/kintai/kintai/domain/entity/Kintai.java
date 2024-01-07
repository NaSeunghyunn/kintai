package com.kintai.kintai.domain.entity;

import com.kintai.kintai.domain.KintaiStatus;
import com.kintai.kintai.util.YearMonthConverter;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.YearMonth;

import static com.kintai.kintai.domain.KintaiStatus.IN_PROGRESS;
import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"member_id", "workYearMonth"})})
@Entity
public class Kintai extends BaseEntity {

    @GeneratedValue(strategy = IDENTITY)
    @Id
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Enumerated(STRING)
    private KintaiStatus status = IN_PROGRESS;

    @Convert(converter = YearMonthConverter.class)
    @Column(nullable = false)
    private YearMonth workYearMonth;

    @Builder
    public Kintai(Long id, Member member, KintaiStatus status, YearMonth workYearMonth) {
        this.id = id;
        this.member = member;
        this.status = status;
        this.workYearMonth = workYearMonth;
    }

    public void submit(KintaiStatus status) {
        this.status = status;
    }
}
