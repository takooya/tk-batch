package com.taikang.opt.db.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

/**
 * @author itw_chenhn
 */
@Entity
@Data
public class VisitCountBySql {
    @Id
    @Column(name = "id")
    private Integer id;
    @Column(name = "company_id")
    private String companyId;
    @Column(name = "visit_date")
    private Date visitDate;
    @Column(name = "staff")
    private String staff;
    @Column(name = "post")
    private String post;
    @Column(name = "org")
    private String org;
}
