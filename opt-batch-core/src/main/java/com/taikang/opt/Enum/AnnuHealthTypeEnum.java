package com.taikang.opt.Enum;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

/**
 * @author itw_wangyc02
 * @date 2019/9/2 11:35
 */
public enum AnnuHealthTypeEnum {
    /**
     * 业务类型
     */
    HEALTH("4001", "健康"),
    WARNING("4002", "警示"),
    FOCUS("4003", "关注"),;

    @Getter
    private String code;
    @Getter
    private String value;

    AnnuHealthTypeEnum(String code, String value) {
        this.code = code;
        this.value = value;
    }

    public static AnnuHealthTypeEnum get(String codeOrValue) {
        if (StringUtils.isBlank(codeOrValue)) {
            return null;
        }
        for (AnnuHealthTypeEnum single : AnnuHealthTypeEnum.values()) {
            if (single.code.equals(codeOrValue.trim()) || single.value.equals(codeOrValue.trim())) {
                return single;
            }
        }
        throw new RuntimeException("不存在的枚举项");
    }
}
