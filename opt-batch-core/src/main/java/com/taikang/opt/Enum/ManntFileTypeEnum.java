package com.taikang.opt.Enum;

import lombok.Data;

/**
 * @author itw_chenhn
 * @date 2019-10-10
 */

public enum ManntFileTypeEnum {
    PASS("3","文件已过期");
    private String code;
    private String message;

    ManntFileTypeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
