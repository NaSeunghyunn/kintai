package com.kintai.kintai.repository;

import com.kintai.kintai.domain.entity.KintaiDetail;
import com.kintai.kintai.repository.custom.KintaiDetailRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KintaiDetailRepository extends JpaRepository<KintaiDetail, Long>, KintaiDetailRepositoryCustom {
}
