package com.cos.photogramstart.handler.ex;

import java.util.Map;

public class CustomValidationException extends RuntimeException{

    //serialVersionUID 객체를 구분할때 사용 JVM이 이용이해함
    private static final long serialVersionUID = 1L;
//    public static long getSerialVersionUID() {
//        return serialVersionUID;
//    }

    //private String message;
    private Map<String, String> errorMap;

    public CustomValidationException(String message, Map<String, String> errorMap){
        super(message);// 부모한테 던져서 message 필요없넹~
        //this.message = message;
        this.errorMap = errorMap;
    }

    public Map<String, String> getErrorMap(){
        return errorMap;
    }




}
