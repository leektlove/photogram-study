package com.cos.photogramstart.web.api;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.subscribe.SubscribeRepository;
import com.cos.photogramstart.service.SubcribeService;
import com.cos.photogramstart.web.dto.CMRespDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;


@RequiredArgsConstructor
@RestController
public class SubscribeApiController {

    private final SubcribeService subcribeService;

    //누가 ? 로그인 한사람(from) 대상을(to) 구독한다
    @PostMapping("/api/subscribe/{toUserId}")
    public ResponseEntity<?> subscribe(@AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable int toUserId){
        Map<String, Object> result = new HashMap<>();

        try {
            subcribeService.구독하기(principalDetails.getUser().getId(), toUserId);

            result.put("toUserId", toUserId);
            result.put("message", "success");
            result.put("code", HttpStatus.OK);

        } catch (Exception e) {
            result.put("message", "fail");
            result.put("code", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new CMRespDto<>(1, "구독 성공", result), HttpStatus.OK);

    }

    @DeleteMapping("/api/subscribe/{toUserId}")
    public ResponseEntity<?> unSubscribe(@AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable int toUserId){
        Map<String, Object> result = new HashMap<>();
        try {
            subcribeService.구독취소하기(principalDetails.getUser().getId(), toUserId);

            result.put("code", HttpStatus.OK);
            result.put("toUserId", toUserId);
            result.put("message", "success");

        } catch (Exception e) {
            result.put("code", HttpStatus.BAD_REQUEST);
            result.put("message", "fail");
        }

        return new ResponseEntity<>(new CMRespDto<>(1, "구독 취소 성공", result), HttpStatus.OK);
    }


}
