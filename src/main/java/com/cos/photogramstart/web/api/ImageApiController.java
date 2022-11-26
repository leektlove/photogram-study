package com.cos.photogramstart.web.api;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.service.ImageService;
import com.cos.photogramstart.service.LikesService;
import com.cos.photogramstart.web.dto.CMRespDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@RequiredArgsConstructor
@RestController
public class ImageApiController {

    private final ImageService imageService;
    private final LikesService likesService;

    @GetMapping("/api/image")
    public ResponseEntity<?> imageStory(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                        @PageableDefault(size = 3) Pageable pageable){

        Map<String, Object> result = new HashMap<>();
        Page<Image> images = imageService.이미지스토리(principalDetails.getUser().getId(), pageable);

        try {
            result.put("message", "success");
            result.put("principalId", principalDetails.getUser().getId());
            result.put("images", images);

        } catch (Exception e) {
            result.put("message", "fail");
        }

        //res.data.content.forEach((image)=>{
        //res.data.images.content.forEach((image)=>{
        return new ResponseEntity<>(new CMRespDto<>(1, "스토리 페이지 이미지 로딩 성공", result), HttpStatus.OK);
        //return new ResponseEntity<>(new CMRespDto<>(1, "스토리 페이지 이미지 로딩 성공", images), HttpStatus.OK);

    }
    // /api/image?page=${page}   Pageable pageable


    @PostMapping("/api/image/{imageId}/likes")
    public ResponseEntity<?> likes(@PathVariable int imageId, @AuthenticationPrincipal PrincipalDetails principalDetails){
        Map<String, Object> result = new HashMap<>();

        try {

            likesService.좋아요(imageId, principalDetails.getUser().getId());

            result.put("code", HttpStatus.CREATED);
            result.put("message", "success");

        } catch (Exception e) {

            result.put("code", HttpStatus.BAD_REQUEST);
            result.put("message", "fail");
        }

        return new ResponseEntity<>(new CMRespDto<>(1,"좋아요 성공", result), HttpStatus.CREATED);
    }


    @DeleteMapping("/api/image/{imageId}/likes")
    public ResponseEntity<?> unLikes(@PathVariable int imageId, @AuthenticationPrincipal PrincipalDetails principalDetails){
        Map<String, Object> result = new HashMap<>();

        try {
            likesService.좋아요취소(imageId, principalDetails.getUser().getId());

            result.put("code", HttpStatus.OK);
            result.put("message", "success");

        } catch (Exception e) {
            result.put("code", HttpStatus.BAD_REQUEST);
            result.put("message", "fail");
        }
        return new ResponseEntity<>(new CMRespDto<>(1,"좋아요 취소 성공", result), HttpStatus.OK);

    }



}
