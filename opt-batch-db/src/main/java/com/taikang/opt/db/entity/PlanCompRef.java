package com.taikang.opt.db.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * 地图与企业关系表
 *
 * @author itw_chenhn
 */
@Entity
@Table(name = "m_plan_comp_ref", schema = "tkquotemaptest", catalog = "")
@IdClass(PlanCompRefPK.class)
@Data
public class PlanCompRef {
    /**
     * 地图id
     */
    @Id
    @Column(name = "mid")
    private Integer mid;
    /**
     * 企业id
     */
    @Id
    @Column(name = "company_id")
    private String companyId;
    /**
     * 审核状态
     */
    @Basic
    @Column(name = "audit_status")
    private String auditStatus;
    /**
     * 补充说明
     */
    @Basic
    @Column(name = "des")
    private String des;
    /**
     * 创建时间
     */
    @Basic
    @Column(name = "create_time")
    private Date createTime;


}
