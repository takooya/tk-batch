package com.taikang.opt.db.repository;

import com.taikang.opt.db.entity.DriverMapDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author itw_chenhn
 */
public interface DriverMapDetailRepository extends JpaRepository<DriverMapDetail, Long> {
    /**
     * 查询开拓企业总数，重点开拓企业数
     */
    DriverMapDetail findByOrgAndIsFocusTrack(String org, String isFocusTrack);


    /**
     * 查询所有所属分公司 org长度为2位
     */
    @Query(value = "SELECT * FROM driver_map_detail WHERE LENGTH(org)= ?1 and is_focus_track = ?2", nativeQuery = true)
    List<DriverMapDetail> orgQuery(Integer length, String focusFlag);

}
