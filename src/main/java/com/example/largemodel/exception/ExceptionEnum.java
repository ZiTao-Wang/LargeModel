package com.example.largemodel.exception;

public enum ExceptionEnum {
    UNKNOWN(9999, "未知系统异常"),
    JSON_PROCESSING(3000, "JSON处理异常"),
    JSON_DESERIALIZATION(3001, "JSON反序列化异常"),
    JSON_SERIALIZATION(3002, "JSON序列化异常"),

    REQ_PARAMS_VALIDATION(4220,"请求入参不能为空"),

    EMPTY(-1 , "");


    private int code;
    private String message;

    ExceptionEnum(int code, String message){
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}