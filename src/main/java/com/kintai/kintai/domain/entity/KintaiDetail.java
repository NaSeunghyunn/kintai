package com.kintai.kintai.domain.entity;

import com.kintai.kintai.domain.WorkType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Entity
public class KintaiDetail extends BaseEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "kintai_id")
    private Kintai kintai;

    @Column(nullable = false)
    private LocalDate date;

    private LocalTime startTime;

    private LocalTime endTime;

    private int breakTimeHours;

    @Enumerated(STRING)
    private WorkType workType;

    private String workDesc;

    private String note;

    @Builder
    public KintaiDetail(Long id, Kintai kintai, LocalDate date, LocalTime startTime, LocalTime endTime, int breakTimeHours, WorkType workType, String workDesc, String note) {
        this.id = id;
        this.kintai = kintai;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.breakTimeHours = breakTimeHours;
        this.workType = workType;
        this.workDesc = workDesc;
        this.note = note;
    }
}
