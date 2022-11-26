package com.cos.photogramstart.service;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.domain.image.ImageRepository;
import com.cos.photogramstart.web.dto.image.ImageUploadDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.Files; // 주의
import java.nio.file.Path; // 주의
import java.nio.file.Paths; // 주의
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ImageService {

    private final ImageRepository imageRepository; //DI를 위해 @RequiredArgsConstructor

    @Value("${file.path}")
    private String uploadFolder;


    //서비스 단에서 데엍베이스 변형을 줄때는 꼭 트랜젝셔널 걸어줘야함
    @Transactional
    public void 사진업로드(ImageUploadDto imageUploadDto, PrincipalDetails principalDetails){

        // 1-3. 파일명 + 확장자 가져오기
        String originalFileName = imageUploadDto.getFile().getOriginalFilename();

        // 1-4. 확장자 추출
        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));


        // 1-5. UUID[Universally Unique IDentifier] : 네트워크 상에서 고유성이 보장되는 id를 만들기 위한 표준 규약. 범용고유식별자.
        UUID uuid = UUID.randomUUID();

        //String imageFineName = uuid + "_" + imageUploadDto.getFile().getOriginalFilename();// 1.jpg

        // 1-6. UUID + 확장자 명으로 저장
        String imageFileName = uuid.toString() + extension;

        System.out.println("이미지 파일이름: " + imageFileName);


        // 주의 java.nio.file.Path   java.nio.file.Paths
        // 1-7. 실제 파일이  저장될 경로 + 파일명 저장.
        Path imageFilePath = Paths.get(uploadFolder + imageFileName);
        System.out.println("imageFilePath: " + imageFilePath);

        //통신, I/O (하드디스크) 가 발생할때는 -> 예외가 발생할수 있다. 런타임시에만 잡을수 있으니 예외처리
        try {
            Files.write(imageFilePath, imageUploadDto.getFile().getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 1-6. 매개변수로 전달된 정보를 Image entity로 변환(Builder 패턴 이용)
        Image image = imageUploadDto.toEntity(imageFileName, principalDetails.getUser());

        // 1-7. image 엔티티 영속화하고 반환받는다.
        imageRepository.save(image);

        //오류 원인
        //System.out.println(imageEntity); // 실제로는 imageEntity.toString()
        //Image 에가서 toString() 오버라이드 후   ", user=" + user + 삭제!!

    }

    // 2-1. 이미지스토리 불러오기 1 - 페이징 X
    @Transactional(readOnly = true)
    public List<Image> 이미지스토리(int principalId) {
        List<Image> images = imageRepository.mStory(principalId);
        return images;
    }

    // 3-1. 이미지스토리 불러오기 2 - 페이징 O
    @Transactional(readOnly = true) // 영속성 컨텍스트 변경 감지해서, 더티체킹, flush(반영) 이짓거리들을 하지 않는다!!
    public Page<Image> 이미지스토리(int principalId, Pageable pageable){
        Page<Image> images = imageRepository.mStory(principalId, pageable);

        //2(cos) 로그인
        // 4-1. images에 좋아요 상태 값 담기
        images.forEach((image)->{
            // 4-2. 이미지에 담겨있는 좋아요 데이터 가져오기
            image.getLikes().forEach((like)->{
                // 4-3. 해당 이미지를 좋아요한 사람이 현재 로그인한 유저라면
                if(like.getUser().getId() == principalId){ // 해당 이미지에 좋아요한 사람들을 찾아서 현재 로그인한 사람이 좋아요 한것인지 비교
                    // 4-4. 좋아요를 한 유저가 로그인한 유저라면 좋아요 상태 값을 true로 세팅 -> 로그인한 유저라면 story 페이지에서  이미지의 좋아요 하트 색깔을 빨간색으로 표시해주기 위해
                    image.setLikestate(true);
                }

                image.setLikecount(image.getLikes().size());

            });


        });

        return images;
    }




    // 4-1. 인기 사진 리스트 불러오기
    @Transactional(readOnly = true)
    public List<Image> 인기사진() {
        return  imageRepository.mPopular();

    }
}
