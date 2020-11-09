package com.taikang.opt.db.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author itw_chenhn
 * companyBase 实体类
 */
@Entity
@Data
@Table(name = "company_base")
public class CompanyBase {
    /**
     * 企业id
     */
    @Id
    @Column(name = "company_id")
    private String companyId;
    /**
     * 年金区（南，中，北）
     */
    @Column(name = "area")
    private String area;
    @Column(name = "area_name")
    private String areaName;
    /**
     * 所属分公司
     */
    @Column(name = "belong_company")
    private String belongCompany;
    /**
     * 所属分公司名字
     */
    @Column(name = "belong_company_name")
    private String belongCompanyName;
    /**
     * 计划类型
     */
    @Column(name = "plan_type")
    private String planType;
    /**
     * 健康状态
     */
    @Column(name = "health_type")
    private String healthType;
    /**
     * 是否重点关注
     */
    @Column(name = "is_focus_track")
    private String isFocusTrack;
    /**
     * 行业分类(编码)
     */
    @Column(name = "industry")
    private String industry;
    /**
     * 行业分类(名称)
     */
    @Column(name = "industry_name")
    private String industryName;
    /**
     * 企业信息是否完善
     */
    @Column(name = "perfect_flag")
    private String perfectFlag;
    /**
     * 企业增补时间
     */
    @Column(name = "commit_date")
    private Date commitDate;
    @Column(name = "finish_status")
    private String finishStatus;
     @Column(name = "join_status")
    private String joinStatus;
}
