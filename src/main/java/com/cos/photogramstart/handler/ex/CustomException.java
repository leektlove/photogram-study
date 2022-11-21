package com.cos.photogramstart.handler.ex;


public class CustomException extends RuntimeException{

    //serialVersionUID 객체를 구분할때 사용 JVM이 이용이해함
    private static final long serialVersionUID = 1L;

    public CustomException(String message){
        super(message);// 부모한테 던져서 message 필요없넹~
    }


}
