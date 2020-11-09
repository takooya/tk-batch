package com.taikang.opt.db.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author itw_yinjm
 * @date 2018/10/23
 */
@Table(name = "m_indust_ctg")
@Entity
@Data
public class IndustCtg implements Serializable {
    private static final long serialVersionUID = -6029965807633399581L;
    /**
     * 编码
     */
    @Id
    @Column(length = 4, name = "ctg_code")
    private String ctgCode;
    /**
     * 名称
     */
    @Column(length = 128, name = "ctg_name")
    private String ctgName;
    /**
     * 父编码
     */
    @Column(length = 4,name = "parent_code")
    private String parentCode;


}
