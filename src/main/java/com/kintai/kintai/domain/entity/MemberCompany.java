package com.kintai.kintai.domain.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

@Getter
@NoArgsConstructor
@Entity
public class MemberCompany extends BaseEntity {

    @GeneratedValue(strategy = IDENTITY)
    @Id
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @Builder
    public MemberCompany(Long id, Member member, Company company) {
        this.id = id;
        this.member = member;
        this.company = company;
    }
}
