package com.cos.photogramstart.web;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.service.UserService;
import com.cos.photogramstart.web.dto.user.UserProfileDto;
import com.nimbusds.jose.proc.SecurityContext;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserService userService;

    @GetMapping("/user/{pageUserId}")
    public String profile(@PathVariable int pageUserId, Model model, @AuthenticationPrincipal PrincipalDetails principalDetails){


        UserProfileDto dto = userService.회원프로필(pageUserId, principalDetails.getUser().getId());
        model.addAttribute("dto", dto);
        return "user/profile";
    }

    //어노테이션으로 세션 접근하기
    @GetMapping("/user/{id}/update")
    public String update(@PathVariable int id, @AuthenticationPrincipal PrincipalDetails principalDetails){
        //poem.xml에 시큐리티 태그 라이브러리 등록
        //header.jsp  활용해 session 전달
        // <sec:authorize access="isAuthenticated()">
        //	<sec:authentication property="principal" var="principal"/>
        //</sec:authorize>
        return "user/update";
    }



    //어노테이션으로 세션 접근하기       모델을 활용해 session 전달
//    @GetMapping("/user/{id}/update")
//    public String update(@PathVariable int id, @AuthenticationPrincipal PrincipalDetails principalDetails, Model model){
//        System.out.println("세션 어노테이션 활용: " + principalDetails.getUser());
//
//        //직접찾은 세션 정보 복잡한 단계 접근법
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        System.out.println("직접찾은 세션 정보 : " + auth.getPrincipal());
//
//        PrincipalDetails mPrincipalDetails = (PrincipalDetails) auth.getPrincipal();
//        System.out.println("직접찾은 세션 정보 2: " + mPrincipalDetails.getUser());
//
//        //모델을 활용해 session 전달
//        model.addAttribute("principal", mPrincipalDetails.getUser());
//
//        return "user/update";
//    }
//



//    @GetMapping("/user/profile")
//    public String profile(){
//        return "user/profile";
//    }

}
