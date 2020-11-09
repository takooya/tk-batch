package com.taikang.opt.db.VO;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author  itw_chenhn
 * 用于封装visitLog 结果
 */
@Data
@Entity
public class visitTempResult {
    @Column(name = "company_id")
    @Id
    private String companyId;
    @Column(name = "visitNum")
    private Integer visitNum;
}
