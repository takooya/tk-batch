package com.taikang.opt.Enum;

import lombok.Getter;

/**
 * @author itw_chenhn
 * @date 2019-11-01
 */
public enum TimeStatusEnum {
    WAITTIME(14,"缓冲器");
    @Getter
    private int code;
    @Getter
    private String value;

    TimeStatusEnum(int code, String value) {
        this.code = code;
        this.value = value;
    }
}
