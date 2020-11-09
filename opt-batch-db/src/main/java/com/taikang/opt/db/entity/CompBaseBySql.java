package com.taikang.opt.db.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * @author itw_chenhn
 * 用于封装
 */
@Entity
@Data
public class CompBaseBySql implements Serializable {
    @Id
    @Column(name = "company_id")
    private String companyId;
    @Column(name = "area_name")
    private String areaName;
    @Column(name = "area")
    private String area;
    @Column(name = "belong_company")
    private String belongCompany;
    @Column(name = "belong_company_name")
    private String belongCompanyName;
    @Column(name = "plan_type")
    private String planType;
    @Column(name = "is_focus_track")
    private String isFocusTrack;
    @Column(name = "industry")
    private String industry;
    @Column(name = "industry_name")
    private String industryName;
    @Column(name = "perfect_flag")
    private String perfectFlag;
    @Column(name = "commit_date")
    private Date commitDate;
    @Column(name = "finish_status")
    private String finishStatus;
}
