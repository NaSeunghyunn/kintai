package com.kintai.kintai.repository;

import com.kintai.kintai.domain.entity.MemberCompany;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MemberCompanyRepository extends JpaRepository<MemberCompany, Long> {
    @Query("SELECT c.name FROM Member m INNER JOIN MemberCompany mc ON mc.member.id = m.id INNER JOIN Company c ON c.id = mc.company.id WHERE m.id = :memberId")
    List<String> findCompanyNames(@Param("memberId") Long memberId);
}
