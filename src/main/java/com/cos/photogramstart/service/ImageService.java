package com.cos.photogramstart.service;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.domain.image.ImageRepository;
import com.cos.photogramstart.web.dto.image.ImageUploadDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
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
