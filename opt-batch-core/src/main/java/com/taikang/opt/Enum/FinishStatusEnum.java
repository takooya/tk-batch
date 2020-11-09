package com.taikang.opt.Enum;

/**
 * @author itw_chenhn
 * @date 2019-10-31
 */
public enum FinishStatusEnum {
    FINISH("1","已完善"),
    UNFINISH("0","未完善");
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

    FinishStatusEnum(String code, String value) {
        this.code = code;
        this.value = value;
    }
}
