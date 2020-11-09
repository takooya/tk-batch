package com.taikang.opt.db.repository;

import com.taikang.opt.db.entity.Orgnization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DepartmentRepository extends JpaRepository<Orgnization, String> {
    @Query(value = "select distinct (name) from dic_province", nativeQuery = true)
    List<String> findProvice();

    //    SELECT * from orgnization where oa_code_name = 'name' or oa_alias_name == 'name'
    @Query(value = "SELECT * from department where oa_code_name = ?1 or oa_alias_name == ?1", nativeQuery = true)
    List<Orgnization> findInstNo(String name);

    /**
     * 根据comCode前部匹配查询,支持索引
     *
     * @param comCode :
     * @return :企业列表
     * @author itw_wangyc02
     * @date 2019/6/19 13:51
     */
    @Query(value = "select * from department where com_code like CONCAT(?1,'%')", nativeQuery = true)
    List<Orgnization> findByComCodeLike(String comCode);

    List<Orgnization> findAllByUpComCode(String code);
}
