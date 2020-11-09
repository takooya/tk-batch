package com.taikang.opt.db.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

/**
 * @author itw_chenhn
 */
@Entity
@Table(name = "visit_count", schema = "tkquotemaptest", catalog = "")
@Data
@DynamicInsert
public class VisitCount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "org")
    private String org;
    @Column(name = "org_category")
    private String orgCategory;
    @Column(name = "area")
    private String area;
    @Column(name = "belong_comp_code")
    private String belongCompCode;
    @Column(name = "project_num")
    private int projectNum;
    @Column(name = "project_count")
    private int projectCount;
    @Column(name = "org_member_num")
    private int orgMemberNum;
    @Column(name = "first_charge_num")
    private int firstChargeNum;
    @Column(name = "part_charge_num")
    private int partChargeNum;
    @Column(name = "support_num")
    private int supportNum;
    @Column(name = "salesman_num")
    private int salesmanNum;
    @Column(name = "create_time")
    private Date createTime;
}
