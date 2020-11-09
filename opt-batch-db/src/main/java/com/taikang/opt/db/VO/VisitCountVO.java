package com.taikang.opt.db.VO;

import lombok.Data;


import java.util.Date;
import java.util.HashSet;

/**
 * @author itw_chenhn
 */
@Data
public class VisitCountVO {
    private String org;
    private String orgCategory;
    private HashSet<String> projectNumSet ;
    private int projectCount;
    private int orgMemberNum;
    private int firstChargeNum;
    private int partChargeNum;
    private int supportNum;
    private int salesmanNum;
    private Date createTime;
    private String area;
    private String belongCompCode;
}
