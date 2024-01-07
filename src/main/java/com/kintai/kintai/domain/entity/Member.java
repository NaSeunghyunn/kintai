package com.kintai.kintai.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Entity
public class Member extends BaseEntity{
    @GeneratedValue(strategy = IDENTITY)
    @Id
    private Long id;

    @Column(nullable = false, unique = true)
    private String loginId;

    @Column(nullable = false)
    private String name;

    @Builder
    public Member(Long id, String loginId, String name) {
        this.id = id;
        this.loginId = loginId;
        this.name = name;
    }
}
