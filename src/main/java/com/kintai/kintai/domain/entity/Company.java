package com.kintai.kintai.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Entity
public class Company extends BaseEntity {

    @GeneratedValue(strategy = IDENTITY)
    @Id
    private Long id;

    @Column(nullable = false)
    private String name;

    @Builder
    public Company(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
