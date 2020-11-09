package com.taikang.opt.db.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @author itw_chenhn
 * VisitBase 实体类
 */
@Entity
@Table(name = "visit_base")
@Data
public class VisitBase {
    /**
     * 主键自增
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 企业id
     */
    @Column(name = "company_id")
    private String companyId;
    /**
     * 拜访时间
     */
    @Column(name = "visit_date")
    private Date visitDate;
    /**
     * 所属分公司
     */
    @Column(name = "belong_comp")
    private String belongComp;
    /**
     * 录入人部门
     */
    @Column(name = "create_dep")
    private String createDep;
    /**
     * 录入人(填写拜访记录的人)
     */
    @Column(name = "creator")
    private String creator;
    /**
     * 拜访公司名
     */
    @Column(name = "comp_name")
    private String compName;
    /**
     * 所属分公司（名字）
     */
    @Column(name = "belong_comp_name")
    private String belongCompName;
    /**
     * 录入人loginId
     */
    @Column(name = "creator_id")
    private String creatorId;
    /**
     * 录入人类型（一责领导）编码
     */
    @Column(name = "creator_type")
    private String creatorType;
    /**
     * 录入人类型（一责领导）名称
     */
    @Column(name = "creator_type_name")
    private String creatorTypeName;
    /**
     * 所属区域（南，中，北）编码
     */
    @Column(name = "area")
    private String area;
    /**
     * 所属区域（南，中，北）名字
     */
    @Column(name = "area_name")
    private String areaName;
    /**
     * 是否重点关注
     */
    @Column(name = "is_focus_track")
    private String isFocusTrack;
    /**
     * 是否结项标志位，0代表未结项，1代表已结项，null 代表该企业从未填写拜访记录
     */
    @Column(name = "finish_flag")
    private String finishStatus;
}
