package com.taikang.opt.db.repository;

import com.taikang.opt.db.entity.DriverCompSort;
import com.taikang.opt.db.entity.DriverCompSortPK;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author itw_chenhn
 */
public interface DriverCompSortRepository extends JpaRepository<DriverCompSort, DriverCompSortPK> {
    DriverCompSort findFirstByYearAndMonthOrderBySortAsc(String year, String month);
}
