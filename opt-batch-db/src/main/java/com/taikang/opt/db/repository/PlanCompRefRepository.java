package com.taikang.opt.db.repository;


import com.taikang.opt.db.entity.PlanCompRef;
import com.taikang.opt.db.entity.PlanCompRefPK;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 地图与企业关联操作接口
 */
public interface PlanCompRefRepository extends JpaRepository<PlanCompRef, PlanCompRefPK> {
    List<PlanCompRef> findByMid(Integer mid);

//    @Query(nativeQuery = "")
//    List<PlanCompRef> findByAddit();

    /**
     *  根据mid和auditStatus查询全部
     * */
    List<PlanCompRef> findAllByMidAndAuditStatus(Integer mid,String auditStatus);

    /**
     * 条件查询
     *
     * @param specification 条件
     * @param pageable      分页
     * @return List<PlanCompRef>
     */
    List<PlanCompRef> findAll(Specification<PlanCompRef> specification, Pageable pageable);

    /**
     * 根据企业,查询地图企业关系
     *
     * @param companyId :企业id
     * @return : 地图企业关系列表
     * @author itw_wangyc02
     * @date 2019/7/24 12:35
     */
    List<PlanCompRef> findByCompanyId(String companyId);

    /**
     * 根据企业,查询地图企业关系
     *
     * @param companyIds :企业id列表
     * @return : 地图企业关系列表
     * @author itw_wangyc02
     * @date 2019/7/24 12:35
     */
    List<PlanCompRef> findByCompanyIdIn(List<String> companyIds);
   /**
    * 根据企业id和地图id查询对应的企业
    *
    * */
    PlanCompRef findByCompanyIdAndMid(String companyid, Integer mid);

    List<PlanCompRef> findByAuditStatusEquals(String auditStatus);
}
