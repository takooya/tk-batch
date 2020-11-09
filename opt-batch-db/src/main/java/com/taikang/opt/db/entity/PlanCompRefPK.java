package com.taikang.opt.db.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * 地图与企业关系外键
 *
 * @author itw_chenhn
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlanCompRefPK implements Serializable {
    /**
     * 地图id
     */
    @Column(name = "mid")
    @Id
    private int mid;
    /**
     * 企业id
     */
    @Column(name = "company_id")
    @Id
    private String companyId;


}
