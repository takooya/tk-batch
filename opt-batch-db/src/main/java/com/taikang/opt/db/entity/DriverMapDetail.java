package com.taikang.opt.db.entity;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

/**
 * @author itw_chenhn
 */
@Entity
@Table(name = "driver_map_detail", schema = "tkquotemaptest", catalog = "")
@Data
public class DriverMapDetail {
    public DriverMapDetail() {
    }


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long id;
    /**
     * 机构（区，所属分公司，总公司）
     */
    @Column(name = "org")
    private String org;
    /**
     * 机构（区，所属分公司，总公司）的名字
     */
    @Column(name = "org_name")
    private String orgName;
    /**
     * 分公司所属哪个区（南，中，北）
     */
    @Column(name = "belong_area")
    private String belongArea;
    /**
     * 分公司所属哪个区（南，中，北）的名字
     */
    @Column(name = "belong_area_name")
    private String belongAreaName;
    /**
     * 公司总数
     */
    @Column(name = "comp_ct")
    private int compCt;
    /**
     * 公司总数占全国公司的比例（这条记录如果是分公司，用分公司企业总数/全国企业总数，同理，区域中心）
     */
    @Column(name = "comp_ctr")
    private Double compCtr;
    /**
     * 适用于分公司企业总数占整个大区的比例
     */
    @Column(name = "area_r")
    private Double areaR;
    /**
     * 单一计划企业总数（区，所属分公司都适用）
     */
    @Column(name = "sp_ct")
    private int spCt;
    /**
     * 集合计划企业总数（区，所属分公司都适用）
     */
    @Column(name = "list_p_ct")
    private int listPCt;
    /**
     * 关注状态企业总数（区，所属分公司都适用）
     */
    @Column(name = "focus_ct")
    private int focusCt;
    /**
     * 警告状态企业总数（区，所属分公司都适用）
     */
    @Column(name = "warn_ct")
    private int warnCt;
    /**
     * 一条记录的录入时间
     */
    @Column(name = "create_time")
    private Date createTime;
    /**
     * 重点开拓企业数量（区，所属分公司都适用），但是，如果切换到重点关注时，前端这列数据不显示
     */
    @Column(name = "key_ct")
    private int keyCt;
    /**
     * 健康企业数量（区，所属分公司都适用）
     */
    @Column(name = "health_ct")
    private int healthCt;
    /**
     * 是否重点关注
     */
    @Column(name = "is_focus_track")
    private String isFocusTrack;
    /**
     * 完善企业信息数（全国级别）
     */
    @Column(name = "perfect_ct")
    private int perfectCt;
    /**
     * 健康类企业占全国企业的比例（区都适用）
     */
    @Column(name = "health_ctr")
    private Double healthCtr;
    /**
     * 警示类企业占本公司（企业总数）比例（所属分公司都适用）
     */
    @Column(name = "warn_self_r")
    private Double warnSelfR;
    /**
     * 警示类企业占区域中心（企业总数）比例（所属分公司都适用）
     */
    @Column(name = "warn_area_r")
    private Double warnAreaR;
    /**
     * 警示类企业占全国（企业总数）比例（区，所属分公司都适用）
     */
    @Column(name = "warn_ctr")
    private Double warnCtr;
    /**
     * 关注企业占本公司（企业总数）比例（所属分公司都适用）
     */
    @Column(name = "concern_self_r")
    private Double concernSelfR;
    /**
     * 关注企业占区域中心（企业总数）比例（所属分公司都适用）
     */
    @Column(name = "concern_area_r")
    private Double concernAreaR;
    /**
     * 关注企业占全国（企业总数）比例（区，所属分公司都适用）
     */
    @Column(name = "concern_ctr")
    private Double concernCtr;
    /**
     *   结项数
     * */
    @Column(name = "finish_ct")
    private int finishCt;


}
