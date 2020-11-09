package com.taikang.opt.db.repository;

import com.taikang.opt.db.entity.VisitCount;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author itw_chenhn
 */
public interface VisitCountRepository extends JpaRepository<VisitCount,Integer> {

}
