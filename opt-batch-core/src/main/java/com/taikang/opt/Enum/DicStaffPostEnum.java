package com.taikang.opt.Enum;

/**
 * visit_base->creator_type
 */
public enum DicStaffPostEnum {
    /**
     * 拜访日志中我司员工姓名职位枚举
     */
    ORGMEMBER("2801", "机构班子成员"),
    FIRSTCHARGE("2802", "一责"),
    PARTCHARGE("2803", "分管总"),
    SUPPORT("2804", "养老金支持部"),
    SALESMAN("2805", "销售人员");
    private String code;
    private String value;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    DicStaffPostEnum(String code, String value) {
        this.code = code;
        this.value = value;
    }
}
