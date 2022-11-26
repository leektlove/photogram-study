package com.cos.photogramstart.web.api;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.handler.ex.CustomValidationApiException;
import com.cos.photogramstart.service.SubcribeService;
import com.cos.photogramstart.service.UserService;
import com.cos.photogramstart.web.dto.CMRespDto;
import com.cos.photogramstart.web.dto.subscribe.SubscribeDto;
import com.cos.photogramstart.web.dto.user.UserUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class UserApiController {
    private final UserService userService;
    private final SubcribeService subcribeService;

    @PutMapping("/api/user/{principalId}/profileimageurl")
    public ResponseEntity<?> profileImageUrlUpdate(@PathVariable int principalId, MultipartFile profileImageFile,
                                                   @AuthenticationPrincipal PrincipalDetails principalDetails
    ){
        Map<String, Object> result = new HashMap<>();

        try {
            // 2-1. 사진 변경하고
            User userEntity = userService.회원프로필사진변경(principalId, profileImageFile);

            // 2-2. 변경된 엔티티를 set
            principalDetails.setUser(userEntity);
            principalDetails.setUser(userEntity); //세션 정보 변경됨~

            result.put("code", HttpStatus.OK);
            result.put("message", "프로필 사진 변경 성공");

        } catch (Exception e) {
            result.put("message", "fail");
        }

        return new ResponseEntity<>(new CMRespDto<>(1,"프로필사진변경 성공", result), HttpStatus.OK);

    }


    @GetMapping("/api/user/{pageUserId}/subscribe")
    public ResponseEntity<?> subscribeList(@PathVariable int pageUserId, @AuthenticationPrincipal PrincipalDetails principalDetails){
        Map<String, Object> result = new HashMap<>();

        List<SubscribeDto> subscribeDtos = subcribeService.구독리스트(principalDetails.getUser().getId(), pageUserId);

        try {
            result.put("code", HttpStatus.OK);
            result.put("message", "구독자 정보 리스트 불러오기 성공");
            result.put("list", subscribeDtos);
        } catch (Exception e) {
            result.put("message", "fail");
        }

        //[[ ${userDto.user.id} ]]
        //res.data.list.forEach((u)=>{
        return new ResponseEntity<>(new CMRespDto<>(1,"구독자 정보 리스트 조회 성공", result), HttpStatus.OK);
        //return new ResponseEntity<>(new CMRespDto<>(1,"구독자 정보 리스트 조회 성공", subscribeDtos), HttpStatus.OK);


    }

    @PutMapping("/api/user/{id}")
    public CMRespDto<?> update(
            @PathVariable int id,
            @Valid UserUpdateDto userUpdateDto,
            BindingResult bindingResult, //위치 중요 @Valid 가 적혀 있는 다음 파라미터에 적어야 됨 !!
            @AuthenticationPrincipal PrincipalDetails principalDetails){
        //ValidationAdvice.ValidationAdvice 가 처리

        User userEntity = userService.회원수정(id, userUpdateDto.toEntity());
        // 1-1. 세션정보도 변경된 엔티티로 업데이트
        principalDetails.setUser(userEntity);

        return new CMRespDto<>(1,"회원수정완료", userEntity); //응답시에 userEntity 모든 gettter 함수가 호출되고 JSON으로 파싱하여 응답한다.

    }


}
