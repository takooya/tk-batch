package com.taikang.opt.Enum;

import lombok.Getter;

public enum InitStatusEnum {
    FINISH("1","完成"),
    UNFINISH("0","未完成");
    @Getter
    private String code;
    @Getter
    private String name;

    InitStatusEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }
}
