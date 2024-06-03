package com.example.largemodel.exception;

public class BusinessException extends RuntimeException {
    private final ExceptionEnum exceptionEnum;

    private BusinessException() {
        super(ExceptionEnum.UNKNOWN.getMessage());
        this.exceptionEnum = ExceptionEnum.UNKNOWN;
    }

    public BusinessException(String message) {
        super(message);
        this.exceptionEnum = ExceptionEnum.UNKNOWN;
    }

    private BusinessException(int code, String message) {
        super(message);
        this.exceptionEnum = ExceptionEnum.EMPTY;
        exceptionEnum.setCode(code);
        exceptionEnum.setMessage(message);
    }

    private BusinessException(Throwable throwable){
        super(throwable);
        this.exceptionEnum = ExceptionEnum.UNKNOWN;
    }

    private BusinessException(ExceptionEnum exceptionEnum, Throwable throwable){
        super(exceptionEnum.getMessage(),throwable);
        this.exceptionEnum = exceptionEnum;
    }

    private BusinessException(ExceptionEnum exceptionEnum){
        super(exceptionEnum.getMessage());
        this.exceptionEnum = exceptionEnum;
    }

    public static BusinessException UNKNOWN_EXCEPTION(){
        return new BusinessException();
    }

    public static BusinessException CUSTOMER_BUSINESS(String message){
        return CUSTOMER_BUSINESS(-1, message);
    }


    public static BusinessException CUSTOMER_BUSINESS(int code,String message){
        return new BusinessException(code, message);
    }

    public static BusinessException DEFAULT_EXCEPTION(String message){
        return new BusinessException(message);
    }

    public static BusinessException WRAPPER_EXCEPTION(Throwable throwable){
        return new BusinessException(throwable);
    }

    public static BusinessException WRAPPER_AND_SURE_EXCEPTION(ExceptionEnum exceptionEnum,Throwable throwable){
        return new BusinessException(exceptionEnum,throwable);
    }

    public static BusinessException SURE_EXCEPTION(ExceptionEnum exceptionEnum){
        return new BusinessException(exceptionEnum);
    }

    public ExceptionEnum getExceptionEnum() {
        return exceptionEnum;
    }
}