package com.kintai.kintai.repository;

import com.kintai.kintai.domain.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> {
}
