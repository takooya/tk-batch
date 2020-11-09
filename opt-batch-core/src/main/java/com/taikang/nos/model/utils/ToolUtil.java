package com.taikang.nos.model.utils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ToolUtil {
    public static void copyProperties(Object source, Object target) {
        log.info("[-ToolUtil-].copyProperties:source={},target={}", source, target);
    }
}
