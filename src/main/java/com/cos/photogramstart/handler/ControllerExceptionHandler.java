package com.cos.photogramstart.handler;

import com.cos.photogramstart.handler.ex.CustomApiException;
import com.cos.photogramstart.handler.ex.CustomException;
import com.cos.photogramstart.handler.ex.CustomValidationApiException;
import com.cos.photogramstart.handler.ex.CustomValidationException;
import com.cos.photogramstart.util.Script;
import com.cos.photogramstart.web.dto.CMRespDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@RestController
@ControllerAdvice
public class ControllerExceptionHandler
{

    //6 script alert 창 뛰우기
    @ExceptionHandler(CustomValidationException.class)
    public String validationException(CustomValidationException e){
        // CMRespDto, Script 비교
        // 1. 클라이언트에게 응답할 때는 Script 좋음. (브라우저)
        // 2. Ajax통신 - CMRespDto
        // 3. Android 통신 - CMRespDto

        if(e.getErrorMap() == null){
            return Script.back(e.getMessage());
        }else{
            return Script.back(e.getErrorMap().toString());
        }
    }

    @ExceptionHandler(CustomException.class)
    public String exception(CustomException e){
        return Script.back(e.getMessage());
    }




    //ExceptionHandler CustomValidationApiException 낚아 챈당~
    @ExceptionHandler(CustomValidationApiException.class)
    public ResponseEntity<?> validationApiException(CustomValidationApiException e){
        return new ResponseEntity<>(new CMRespDto<>(-1,e.getMessage(), e.getErrorMap()), HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(CustomApiException.class)
    public ResponseEntity<?> apiException(CustomApiException e){
        return new ResponseEntity<>(new CMRespDto<>(-1,e.getMessage(), null), HttpStatus.BAD_REQUEST);
    }



//    @ExceptionHandler(CustomValidationApiException.class)
//    public CMRespDto<?> validationApiException(CustomValidationApiException e){
//        // CMRespDto, Script 비교
//        // 1. 클라이언트에게 응답할 때는 Script 좋음. (브라우저)
//        // 2. Ajax통신 - CMRespDto
//        // 3. Android 통신 - CMRespDto
//        return new CMRespDto<>(-1,e.getMessage(), e.getErrorMap());
//    }



//    @ExceptionHandler(RuntimeException.class)
//    public String validationException(RuntimeException e){
//        return e.getMessage();
//    }

    //1 CustomValidationException 사용하겠다 .
//    @ExceptionHandler(CustomValidationException.class)
//    public Map<String, String> validationException(CustomValidationException e){
//        return e.getErrorMap();
//    }

    //2 메세지를 같이 띄우고 싶다. ----
//    @ExceptionHandler(CustomValidationException.class)
//    public CMRespDto validationException(CustomValidationException e){
//        return new CMRespDto(e.getMessage(), e.getErrorMap());
//    }

    //3 제너릭 사용
//    @ExceptionHandler(CustomValidationException.class)
//    public CMRespDto<Map<String, String>> validationException(CustomValidationException e){
//        return new CMRespDto(-1, e.getMessage(), e.getErrorMap());
//    }

    //3 만약 문자열을 리턴하고 싶다.
//    @ExceptionHandler(CustomValidationException.class)
//    public CMRespDto<String> validationException(CustomValidationException e){
//        return new CMRespDto(-1, e.getMessage(), "문자열을 리턴하고 싶다.");
//    }

    //4 추론할 수 있다.
//    @ExceptionHandler(CustomValidationException.class)
//    public CMRespDto<?> validationException(CustomValidationException e){
//        return new CMRespDto(-1, e.getMessage(), e.getErrorMap());
//    }

    //5 추론할 수 있다. 2
//    @ExceptionHandler(CustomValidationException.class)
//    public CMRespDto<?> validationException(CustomValidationException e){
//        return new CMRespDto<Map<String, String>>(-1, e.getMessage(), e.getErrorMap());
//    }



}
