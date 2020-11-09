package com.taikang.opt.db.repository;

import com.taikang.opt.db.VO.visitTempResult;
import com.taikang.opt.db.entity.VisitLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

/**
 * 拜访日志接口
 */
public interface VisitLogRepository extends JpaRepository<VisitLog, Long> {
    List<VisitLog> findByCompanyId(String companyId);

    Page<VisitLog> findAll(Specification<VisitLog> specification, Pageable pageable);

    List<VisitLog> findByCompanyIdIn(Iterable<String> companyIds);


   @Query(value = "SELECT\n" +
           "\tcompany_id,\n" +
           "\tcount(company_id) AS visitNum\n" +
           "FROM\n" +
           "\tmannt_visit_log\n" +
           "WHERE\n" +
           "\tvisit_date BETWEEN ?1\n" +
           "AND ?2 and audit_status = 1 \n" +
           "GROUP BY\n" +
           "\tcompany_id ",nativeQuery = true)
   List<Map> compVisitQuery(String startTime, String endTime);
}
