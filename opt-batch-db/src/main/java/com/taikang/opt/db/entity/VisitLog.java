package com.taikang.opt.db.entity;

import lombok.Data;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import javax.persistence.*;
import java.sql.Date;

/**
 * @Author:itw_wangyb03
 * @Date:2019/9/2
 * @Description:拜访日志信息表
 */
@Entity
@Table(name = "mannt_visit_log")
@Data
public class VisitLog {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    /**
     * 企业id
     */
    @Basic
    @Column(name = "company_id", nullable = false)
    private String companyId;

    /**
     * 拜访日期
     */
    @Basic
    @Column(name = "visit_date", nullable = true)
    private Date visitDate;


    /**
     * 拜访形式
     */
    @Basic
    @Column(name = "visit_type", nullable = true, length = 16)
    private String visitType;

    /**
     * 沟通纪要
     */
    @Basic
    @Column(name = "summary", nullable = true, length = 1024)
    private String summary;

    /**
     * 存在问题
     */
    @Basic
    @Column(name = "problems", nullable = true, length = 1024)
    private String problems;

    /**
     * 下一步拜访计划日期
     */
    @Basic
    @Column(name = "visit_plan_date", nullable = true)
    private Date visitPlanDate;

    /**
     * 下一步拜访计划沟通纪要
     */
    @Basic
    @Column(name = "visit_plan_content", nullable = true, length = 1024)
    private String visitPlanContent;

    /**
     * 录入时间
     */
    @Basic
    @Column(name = "create_time", nullable = true)
    @Generated(GenerationTime.INSERT)
    private Date createTime;

    /**
     * 录入人
     */
    @Basic
    @Column(name = "creator", nullable = true, length = 32)
    private String creator;
}
