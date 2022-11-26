package com.cos.photogramstart.web;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.handler.ex.CustomValidationException;
import com.cos.photogramstart.service.ImageService;
import com.cos.photogramstart.web.dto.image.ImageUploadDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;


@RequiredArgsConstructor
@Controller
public class ImageController {

    private final ImageService imageService;

    @GetMapping({"/", "image/story"})
    public String story(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {
        model.addAttribute("principal", principalDetails.getUser());//principalDetails.getUser()
        return "image/story";
    }
//    public String story(){
//        return "image/story";
//    }

    //API 구현한다면 ~이유는~ (브라우저에서 요청하는게 아니라, 안드로이드, iOS 요청 한다면 API)
    @GetMapping({ "image/popular"})
    public String popular(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {
//    public String popular(Model model){
        //api는 데이터를 리턴하는 서버!! 그래서 Controller  모델에 담고 가져가기만 하면됨
        List<Image> images = imageService.인기사진();
        model.addAttribute("images", images);
        model.addAttribute("principal", principalDetails.getUser());// principalDetails.getUser()

        return "image/popular";
    }

    @GetMapping({ "image/upload"})
    public String upload(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {
        model.addAttribute("principal", principalDetails.getUser());// principalDetails.getUser()

        return "image/upload";
    }
//    public String upload(){
//        return "image/upload";
//    }


    @PostMapping("/image")
    public String imageUpload(ImageUploadDto imageUploadDto, @AuthenticationPrincipal PrincipalDetails principalDetails){
        //서비스 호출
        if(imageUploadDto.getFile().isEmpty()){
            throw new CustomValidationException("이미지가 첨부되지 않았습니다.", null);// 페이지 응답이 필요 api가 아님
        }

        imageService.사진업로드(imageUploadDto, principalDetails);
        //imageService.imageUpload(imageUploadDto, principalDetails);

        return "redirect:/user/" + principalDetails.getUser().getId();

    }


}
