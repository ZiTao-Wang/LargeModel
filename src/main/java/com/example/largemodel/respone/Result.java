package com.example.largemodel.respone;


import com.example.largemodel.exception.ExceptionEnum;

import java.io.Serializable;
import java.time.LocalDateTime;

public final class Result implements Serializable {

    private final int code;
    private final String message;
    private LocalDateTime localDateTime;
    private Object data;

    private Result(){
        this.code = 2000;
        this.message = "SUCCESS";
        this.localDateTime = LocalDateTime.now();
    }

    private Result(Object data){
        this();
        this.data = data;
    }

    private Result(int code, String message, Object data){
        this.code = code;
        this.message = message;
        this.data = data;
        this.localDateTime = LocalDateTime.now();
    }

    public static Result SUCCESS_NON_DATA(){
        return new Result();
    }

    public static Result SUCCESS(Object data){
        return new Result(data);
    }

    public static Result SUCCESS(){
        return new Result();
    }

    public static Result DEFAULT_FAIL(){
        return new Result(ExceptionEnum.UNKNOWN.getCode(), ExceptionEnum.UNKNOWN.getMessage(), null);
    }

    public static Result FAIL(int code,String message){
        return new Result(code, message, null);
    }
    public static Result FAIL(ExceptionEnum exceptionEnum){
        return new Result(exceptionEnum.getCode(), exceptionEnum.getMessage(),null);
    }

    public static Result FAIL_DATA(int code,String message,Object data){
        return new Result(code, message, data);
    }

    public static Result RESULT_SURE(int code,String message,Object data){
        return new Result(code, message, data);
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public  Object getData() {
        return data;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }
}
