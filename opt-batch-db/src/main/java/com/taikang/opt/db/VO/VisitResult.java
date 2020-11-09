package com.taikang.opt.db.VO;

import lombok.Data;

/**
 * @author itw_chenhn
 *  封装企业的拜访次数（两周，一个月）
 */
@Data
public class VisitResult {
    private String companyId;
    private Integer twoWeekVisitNum;
    private Integer oneMonthVisitNum;

    public VisitResult() {
    }

    public VisitResult(String companyId) {
        this.companyId = companyId;
    }

    public VisitResult(String companyId, Integer twoWeekVisitNum, Integer oneMonthVisitNum) {
        this.companyId = companyId;
        this.twoWeekVisitNum = twoWeekVisitNum;
        this.oneMonthVisitNum = oneMonthVisitNum;
    }

}
