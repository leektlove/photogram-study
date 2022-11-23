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
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ImageService {

    private final ImageRepository imageRepository; //DI를 위해 @RequiredArgsConstructor

    @Transactional(readOnly = true) // 영속성 컨텍스트 변경 감지해서, 더티체킹, flush(반영) 이짓거리들을 하지 않는다!!
    public Page<Image> 이미지스토리(int principalId, Pageable pageable){
        Page<Image> images = imageRepository.mStory(principalId, pageable);

        //2(cos) 로그인
        //images에 좋아요 상태 담기
        images.forEach((image)->{

            image.setLikeCount(image.getLikes().size());

            image.getLikes().forEach((like)->{
                if(like.getUser().getId() == principalId){ // 해당 이미지에 좋아요한 사람들을 찾아서 현재 로그인한 사람이 좋아요 한것인지 비교
                    image.setLikeState(true);
                }
            });
        });

        return images;
    }


    @Value("${file.path}")
    private String uploadFolder;


    //서비스 단에서 데엍베이스 변형을 줄때는 꼭 트랜젝셔널 걸어줘야함
    @Transactional
    public void 사진업로드(ImageUploadDto imageUploadDto, PrincipalDetails principalDetails){

        UUID uuid = UUID.randomUUID();
        String imageFineName = uuid + "_" + imageUploadDto.getFile().getOriginalFilename();// 1.jpg
        System.out.println("이미지 파일이름: " + imageFineName);

        // 주의 java.nio.file.Path   java.nio.file.Paths
        Path imageFilePath = Paths.get(uploadFolder+imageFineName);

        //통신, I/O (하드디스크) 가 발생할때는 -> 예외가 발생할수 있다. 런타임시에만 잡을수 있으니 예외처리
        try {
            // 주의 java.nio.file.Files
            Files.write(imageFilePath, imageUploadDto.getFile().getBytes());
        }catch (Exception e){
            e.printStackTrace();
        }

        //image 테이블에 저장
        Image image = imageUploadDto.toEntity(imageFineName, principalDetails.getUser()); // 3fe83d60-dc3b-4e31-8257-8dd2292a0ae2_EL_f6597bf7d3324ff1af43e21cae1c148c.jpg

        Image imageEntity = imageRepository.save(image);

        //오류 원인
        //System.out.println(imageEntity); // 실제로는 imageEntity.toString()
        //Image 에가서 toString() 오버라이드 후   ", user=" + user + 삭제!!


    }

}
