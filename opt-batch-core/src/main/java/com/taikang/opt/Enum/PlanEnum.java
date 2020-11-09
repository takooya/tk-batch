package com.taikang.opt.Enum;

import lombok.Data;

/**
 * @author itw_chenhn
 */
public enum PlanEnum {
    ANNUITYPLAN("年金作战地图",65);
    private String name;
    private Integer value;

    public String getName() {
        return name;
    }

    public Integer getValue() {
        return value;
    }

    PlanEnum(String name, Integer value) {
        this.name = name;
        this.value = value;
    }
}
