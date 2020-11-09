package com.taikang.opt.db.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author itw_yinjm
 * @date 2018/9/14
 */
@Data
@Entity
@Table(name = "dic_orgnization")
public class Orgnization implements Serializable {
    private static final long serialVersionUID = -2219470714319460531L;
    /**
     * 四位机构码
     */
    @Id
    @Column(length = 8,name = "com_code")
    private String comCode;
    /**
     * 二位机构码
     */
    @Column(length = 4,name = "up_com_code")
    private String upComCode;
    /**
     * 名称
     */
    @Column(length = 255,name = "name")
    private String name;
    /**
     * 简称
     */
    @Column(length = 128,name = "short_name")
    private String shortName;
    /**
     *   所属区域
     * */
    @Column(name = "area")
    private String area;
    /**
     *   所属区域名字
     * */
    @Column(name = "area_name")
    private String areaName;
    /**
     *  战略分类
     * */
    @Column(name = "org_category")
    private String orgCategory;


//    @Transient
//    private List<Orgnization> subDeps = new ArrayList<>();

}
