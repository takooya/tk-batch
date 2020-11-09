package com.taikang.nos.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseData {
    private int code;
    private String message;

    public ResponseData(int code) {
        this.code = code;
    }

    public ResponseData(String message) {
        this.message = message;
    }

    public static ResponseData ok() {
        return new ResponseData(200);
    }

    public static ResponseData error() {
        return new ResponseData(500);
    }

    public ResponseData data(String message) {
        this.setMessage(message);
        return this;
    }
}
