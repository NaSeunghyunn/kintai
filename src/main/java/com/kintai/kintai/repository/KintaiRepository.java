package com.kintai.kintai.repository;

import com.kintai.kintai.domain.entity.Kintai;
import com.kintai.kintai.repository.custom.KintaiRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KintaiRepository extends JpaRepository<Kintai, Long>, KintaiRepositoryCustom {
}
