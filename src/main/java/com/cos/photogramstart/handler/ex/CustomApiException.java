package com.cos.photogramstart.handler.ex;

import java.util.Map;

public class CustomApiException extends RuntimeException{

    //serialVersionUID 객체를 구분할때 사용 JVM이 이용이해함
    private static final long serialVersionUID = 1L;

    private Map<String, String> errorMap;
    public CustomApiException(String message){
        super(message);
    }

}
