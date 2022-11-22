package com.cos.photogramstart.web;

import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.handler.ex.CustomValidationException;
import com.cos.photogramstart.service.AuthService;
import com.cos.photogramstart.web.dto.auth.SignupDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor // final 필드를 DI할때 사용
@Controller // 1. IoC, 2. 파일을 리턴하는 컨트롤러
public class AuthController {

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    //@Autowired

    //DI~
    //final 키워드 사용후  @RequiredArgsConstructor
    private final AuthService authService;
    //DI의존성 주입 생성자 코드하기 귀찮음
//    public AuthController(AuthService authService){
//        this.authService = authService;
//    }


    @GetMapping("/auth/signin")
    public String signinForm(){
        return "auth/signin";
    }

    @GetMapping("/auth/signup")
    public String signupForm(){
        return "auth/signup";
    }

    // 회원가입버튼 -> /auth/signup -> /auth/signin
    // 회원가입버튼 X
//    @PostMapping("/auth/signup")
//    public String signup(){
//        //회원가입 성공하면 로그인 페이지
//        System.out.println("signup 실행됨");
//        return "auth/signin";
//    }

    @PostMapping("/auth/signup")
    public String signup(@Valid SignupDto signupDto, BindingResult bindingResult){// key=value(x-www-form-urlencoded)
        //log.info(signupDto.toString());
        //bindingResult 기 유효성 검사에러시
        //FieldError 컬렉션에 담는다.

        if(bindingResult.hasErrors()){
            Map<String, String> errorMap = new HashMap<>();
            for(FieldError error:bindingResult.getFieldErrors()){
                errorMap.put(error.getField(), error.getDefaultMessage());
                System.out.println("=============================");
                System.out.println(error.getDefaultMessage());
                System.out.println("=============================");
            }

            //
            //throw new RuntimeException("유효성검사 실패함");
            //
            //Custom
            throw new CustomValidationException("유효성검사 실패함", errorMap);

        }else{
            //User <- SignupDto
            User user = signupDto.toEntity();
            User userEntity = authService.userSign(user);
            System.out.println(userEntity);
            log.info(user.toString());
            return "auth/signin";// 파일
        }


    }

}
