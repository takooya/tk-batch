package com.taikang.opt.db.repository;

import com.taikang.opt.db.entity.VisitBase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author itw_chenhn
 * @date 2019-10-16
 */
public interface VisitBaseRepository extends JpaRepository<VisitBase,Long> {
}
